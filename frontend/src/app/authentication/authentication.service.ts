import { User } from './store/user';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { autoLogout } from './store/authentication.actions';
import { AuthState } from './store/authentication.state';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
    timeoutInterval: any;
    
    constructor(private http: HttpClient, private store: Store<AuthState>) {}


  login(username: string, password: string, rememberMe: boolean): Observable<User> {
    return this.http.post<User>('http://backend:8081/api/authenticate', { username, password, rememberMe});
  }

  signUp(login: string, firstName: string, lastName: string, email: string): Observable<User> {
    return this.http.post<User>('http://backend:8081/api//api/admin/users', { login, firstName, lastName, email});
  }



  getErrorMessage(message: string) {
    switch (message) {
      case 'USERNAME_NOT_FOUND':
        return 'Username Not Found';
      case 'INVALID_PASSWORD':
        return 'Invalid Password';
      case 'USERNAME_EXISTS':
        return 'USername already exists';
      default:
        return 'Unknown error occurred. Please try again';
    }
  }

  setUserInLocalStorage(user: User) {
    localStorage.setItem('userData', JSON.stringify(user));
    this.runTimeoutInterval(user);
  }

  runTimeoutInterval(user: User) {
    const todaysDate = new Date().getTime();
    const expirationDate = user.expirationDate;
    const timeInterval = expirationDate - todaysDate;

    this.timeoutInterval = setTimeout(() => {
      this.store.dispatch(autoLogout());
    }, timeInterval);
  }

  getUserFromLocalStorage() {
    const userDataString = localStorage.getItem('userData');
    if (userDataString) {
      const userData = JSON.parse(userDataString);
      const expirationDate = userData.expirationDate;
      const user : User = {
        token : userData.token,
        authority: userData.authority,
        expirationDate: expirationDate,
      };
      this.runTimeoutInterval(user);
      return user;
    }
    return null;
  }

  logout() {
    localStorage.removeItem('userData');
    if (this.timeoutInterval) {
      clearTimeout(this.timeoutInterval);
      this.timeoutInterval = null;
    }
  }
}

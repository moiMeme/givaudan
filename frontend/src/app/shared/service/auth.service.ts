import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { AuthenticationService } from 'src/app/authentication/authentication.service';

@Injectable()
export class AuthService {

  accessToken: String | undefined;
  expiresAt: Number | undefined;

  constructor(public router: Router, private authenticationService: AuthenticationService) {}

  public isAuthenticated(): boolean {
    const user = this.authenticationService.getUserFromLocalStorage();
    if (user) {
      user.expirationDate
      if (new Date().getTime() < this.expiresAt) {
        return false;
      }
      return true;
    }
    return false;

  }

  public getToken(): string {
    const user = this.authenticationService.getUserFromLocalStorage();
    if (user) {
      return user.token;
    }
    return null;

  }
}

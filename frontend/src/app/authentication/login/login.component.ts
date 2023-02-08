import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { setLoadingSpinner, loginStart } from '../store/authentication.actions';
import { AuthState } from '../store/authentication.state';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private store: Store<AuthState>) {}

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }
  onLoginSubmit() {
    const username = this.loginForm.value.username;
    const password = this.loginForm.value.password;
    const rememberMe = this.loginForm.value.rememberMe;
    this.store.dispatch(setLoadingSpinner({ status: true }));
    this.store.dispatch(loginStart({ username, password, rememberMe }));
  }
}

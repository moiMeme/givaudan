import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { setAPIStatus } from 'src/app/shared/store/app.action';
import { selectAppState } from 'src/app/shared/store/app.selector';
import { Appstate } from 'src/app/shared/store/appstate';
import { invokeSaveNewContactAPI } from '../store/contacts.action';

import { Contacts } from '../store/contacts';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent implements OnInit {
  constructor(
    private store: Store,
    private appStore: Store<Appstate>,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  addForm: FormGroup;
  contactForm: Contacts = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    zipCode: undefined,
    birthDate: new Date
  };

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      lastName: [null, Validators.required],
      firstName: [null, Validators.required],
      zipCode: [null, Validators.required],
      address: [null, Validators.required],
      birthDate: [null, Validators.required],
      phoneNumber: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  save() {
    this.store.dispatch(invokeSaveNewContactAPI({ newContact: this.contactForm }));
    let apiStatus$ = this.appStore.pipe(select(selectAppState));
    apiStatus$.subscribe((apState) => {
      if (apState.apiStatus == 'success') {
        this.appStore.dispatch(
          setAPIStatus({ apiStatus: { apiResponseMessage: '', apiStatus: '' } })
        );
         this.router.navigate(['/']);
      }
    });
  }
}

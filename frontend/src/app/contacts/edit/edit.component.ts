import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { switchMap } from 'rxjs';
import { setAPIStatus } from 'src/app/shared/store/app.action';
import { selectAppState } from 'src/app/shared/store/app.selector';
import { Appstate } from 'src/app/shared/store/appstate';
import { Contacts } from '../store/contacts';
import { invokeUpdateContactAPI } from '../store/contacts.action';
import { selectContactById } from '../store/contacts.selector';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css'],
})
export class EditComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private store: Store,
    private appStore: Store<Appstate>,
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

    let fetchData$ = this.route.paramMap.pipe(
      switchMap((params) => {
        var id = Number(params.get('id'));
        return this.store.pipe(select(selectContactById(id)));
      })
    );
    fetchData$.subscribe((data) => {
      if (data) {
        this.contactForm = { ...data };
      }
      else{
        this.router.navigate(['/']);
      }
    });
  }

  udapte() {
    this.store.dispatch(
      invokeUpdateContactAPI({ updateContact: { ...this.contactForm } })
    );
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

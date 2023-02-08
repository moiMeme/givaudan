import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';
import { EMPTY, map, mergeMap, switchMap, withLatestFrom } from 'rxjs';
import { setAPIStatus } from 'src/app/shared/store/app.action';
import { Appstate } from 'src/app/shared/store/appstate';
import { ContactsService } from '../contacts.service';
import {
  contactsFetchAPISuccess,
  deleteContactAPISuccess,
  invokeContactsAPI,
  invokeDeleteContactAPI,
  invokeSaveNewContactAPI,
  invokeUpdateContactAPI,
  saveNewContactAPISucess,
  updateContactAPISucess,
} from './contacts.action';
import { selectContacts } from './contacts.selector';

@Injectable()
export class ContactsEffect {
  constructor(
    private actions$: Actions,
    private contactsService: ContactsService,
    private store: Store,
    private appStore: Store<Appstate>
  ) {}

  loadAllContacts$ = createEffect(() =>
    this.actions$.pipe(
      ofType(invokeContactsAPI),
      withLatestFrom(this.store.pipe(select(selectContacts))),
      mergeMap(([, contactformStore]) => {
        if (contactformStore.length > 0) {
          return EMPTY;
        }
        return this.contactsService
          .get()
          .pipe(map((data) => contactsFetchAPISuccess({ allContacts: data })));
      })
    )
  );

  saveNewContact$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(invokeSaveNewContactAPI),
      switchMap((action) => {
        this.appStore.dispatch(
          setAPIStatus({ apiStatus: { apiResponseMessage: '', apiStatus: ''} })
        );
        return this.contactsService.create(action.newContact).pipe(
          map((data) => {
            this.appStore.dispatch(
              setAPIStatus({
                apiStatus: { apiResponseMessage: '', apiStatus: 'success' },
              })
            );
            return saveNewContactAPISucess({ newContact: data });
          })
        );
      })
    );
  });

  updateContactAPI$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(invokeUpdateContactAPI),
      switchMap((action) => {
        this.appStore.dispatch(
          setAPIStatus({ apiStatus: { apiResponseMessage: '', apiStatus: '' } })
        );
        return this.contactsService.update(action.updateContact).pipe(
          map((data) => {
            this.appStore.dispatch(
              setAPIStatus({
                apiStatus: { apiResponseMessage: '', apiStatus: 'success' },
              })
            );
            return updateContactAPISucess({ updateContact: data });
          })
        );
      })
    );
  });

  deleteContactsAPI$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(invokeDeleteContactAPI),
      switchMap((actions) => {
        this.appStore.dispatch(
          setAPIStatus({ apiStatus: { apiResponseMessage: '', apiStatus: '' } })
        );
        return this.contactsService.delete(actions.id).pipe(
          map(() => {
            this.appStore.dispatch(
              setAPIStatus({
                apiStatus: { apiResponseMessage: '', apiStatus: 'success' },
              })
            );
            return deleteContactAPISuccess({ id: actions.id });
          })
        );
      })
    );
  });
}

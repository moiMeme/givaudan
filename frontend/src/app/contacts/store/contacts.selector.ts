import { createFeatureSelector, createSelector } from '@ngrx/store';
import { Contacts } from './contacts';

export const selectContacts = createFeatureSelector<Contacts[]>('mycontacts');

export const selectContactById = (contactId: number) =>
  createSelector(selectContacts, (contacts: Contacts[]) => {
    var contactbyId = contacts.filter((_) => _.id == contactId);
    if (contactbyId.length == 0) {
      return null;
    }
    return contactbyId[0];
  });

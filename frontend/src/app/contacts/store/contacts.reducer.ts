import { createReducer, on } from '@ngrx/store';
import { Contacts } from './contacts';
import { contactsFetchAPISuccess, deleteContactAPISuccess, saveNewContactAPISucess, updateContactAPISucess } from './contacts.action';

export const initialState: ReadonlyArray<Contacts> = [];

export const contactReducer = createReducer(
  initialState,
  on(contactsFetchAPISuccess, (state, { allContacts }) => {
    return allContacts;
  }),
  on(saveNewContactAPISucess, (state, { newContact }) => {
    let newState = [...state];
    newState.unshift(newContact);
    return newState;
  }),
  on(updateContactAPISucess, (state, { updateContact }) => {
    let newState = state.filter((_) => _.id != updateContact.id);
    newState.unshift(updateContact);
    return newState;
  }),
  on(deleteContactAPISuccess, (state, { id }) => {
    let newState =state.filter((_) => _.id != id);
    return newState;
  })
);

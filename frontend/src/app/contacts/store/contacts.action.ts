import { createAction, props } from '@ngrx/store';
import { Contacts } from './contacts';

export const invokeContactsAPI = createAction(
  '[Contacts API] Invoke Contacts Fetch API'
);

export const contactsFetchAPISuccess = createAction(
  '[Contacts API] Fetch API Success',
  props<{ allContacts: Contacts[] }>()
);

export const invokeSaveNewContactAPI = createAction(
  '[Contacts API] Inovke save new Contact api',
  props<{ newContact: Contacts }>()
);

export const saveNewContactAPISucess = createAction(
  '[Contacts API] save new contact api success',
  props<{ newContact: Contacts }>()
);

export const invokeUpdateContactAPI = createAction(
  '[Contacts API] Inovke update contact api',
  props<{ updateContact: Contacts }>()
);

export const updateContactAPISucess = createAction(
  '[Contacts API] update  contact api success',
  props<{ updateContact: Contacts }>()
);

export const invokeDeleteContactAPI = createAction(
  '[Contacts API] Inovke delete contact api',
  props<{id:number}>()
);

export const deleteContactAPISuccess = createAction(
  '[Contacts API] deleted contact api success',
  props<{id:number}>()
);
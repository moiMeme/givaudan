import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contacts } from './store/contacts';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {

  private BASE_URL = 'http://backend:8081';

  constructor(private http: HttpClient) {}
  get() {
    const url = `${this.BASE_URL}/api/contacts`;
    return this.http.get<Contacts[]>(url);
  }

  create(payload: Contacts) {
    const url = `${this.BASE_URL}/api/contacts`;
    return this.http.post<Contacts>(url, payload);
  }

  update(payload: Contacts) {
    const url = `${this.BASE_URL}/api/contacts/${payload.id}`;
    return this.http.put<Contacts>(url, payload);
  }

  delete(id: number) {
    const url = `${this.BASE_URL}/api/contacts/${id}`;
    return this.http.delete(url);
  }
}

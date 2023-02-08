import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../shared/material.module';

import { ContactsRoutingModule } from './contacts-routing.module';
import { HomeComponent } from './home/home.component';
import { StoreModule } from '@ngrx/store';
import { contactReducer } from './store/contacts.reducer';
import { EffectsModule } from '@ngrx/effects';
import { ContactsEffect } from './store/contacts.effect';
import { AddComponent } from './add/add.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditComponent } from './edit/edit.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DeleteComponent } from './delete/delete.component';

@NgModule({
  declarations: [HomeComponent, AddComponent, EditComponent, DeleteComponent],
  imports: [
    CommonModule,
    ContactsRoutingModule,
    FormsModule,
    MaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    StoreModule.forFeature('mycontacts', contactReducer),
    EffectsModule.forFeature([ContactsEffect])
  ],
})
export class ContactsModule {}

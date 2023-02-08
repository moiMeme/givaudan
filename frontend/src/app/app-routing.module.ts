import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/service/auth.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./contacts/contacts.module').then((b) => b.ContactsModule),
      canActivate: [AuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./authentication/authentication.module').then((b) => b.AuthenticationModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

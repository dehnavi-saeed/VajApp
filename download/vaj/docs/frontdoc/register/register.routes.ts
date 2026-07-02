import { Routes } from '@angular/router';

export const REGISTER_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./register-page/register-page.component').then(
        (m) => m.RegisterPageComponent,
      ),
  },
];
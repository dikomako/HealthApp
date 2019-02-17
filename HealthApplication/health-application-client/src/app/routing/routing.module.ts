import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../services/auth.guard';

import { LoginComponent } from '../login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
  },
];

@NgModule({
  imports: [ 
    RouterModule.forRoot(routes),
  ],
  exports: [ 
    RouterModule,
   ],
  declarations: []
})
export class RoutingModule { }

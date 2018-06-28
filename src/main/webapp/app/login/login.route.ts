import { Route } from '@angular/router';

import { LoginComponent } from './';

export const LOGIN_ROUTE: Route = {
    path: 'login',
    component: LoginComponent,
    data: {
        authorities: [],
        pageTitle: 'Login'
    }
};

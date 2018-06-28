import { Route } from '@angular/router';
import { HomeComponent } from './home.component';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Welcome, Java Hipster!'
    },
    canActivate: [UserRouteAccessService]
};

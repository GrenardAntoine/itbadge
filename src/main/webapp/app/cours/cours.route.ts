import { Route } from '@angular/router';
import { CoursComponent } from './cours.component';
import { UserRouteAccessService } from '../core/auth/user-route-access-service';

export const COURS_ROUTE: Route = {
    path: 'cours',
    component: CoursComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ItBadge - Cours'
    },
    canActivate: [UserRouteAccessService]
};

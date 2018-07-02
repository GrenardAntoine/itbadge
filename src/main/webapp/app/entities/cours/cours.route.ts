import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Cours } from 'app/shared/model/cours.model';
import { CoursService } from './cours.service';
import { CoursComponent } from './cours.component';
import { CoursDetailComponent } from './cours-detail.component';
import { CoursUpdateComponent } from './cours-update.component';
import { CoursDeletePopupComponent } from './cours-delete-dialog.component';
import { ICours } from 'app/shared/model/cours.model';

@Injectable({ providedIn: 'root' })
export class CoursResolve implements Resolve<ICours> {
    constructor(private service: CoursService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((cours: HttpResponse<Cours>) => cours.body);
        }
        return Observable.of(new Cours());
    }
}

export const coursRoute: Routes = [
    {
        path: 'adminCours',
        component: CoursComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Cours'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adminCours/:id/view',
        component: CoursDetailComponent,
        resolve: {
            cours: CoursResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cours'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adminCours/new',
        component: CoursUpdateComponent,
        resolve: {
            cours: CoursResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cours'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adminCours/:id/edit',
        component: CoursUpdateComponent,
        resolve: {
            cours: CoursResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cours'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coursPopupRoute: Routes = [
    {
        path: 'adminCours/:id/delete',
        component: CoursDeletePopupComponent,
        resolve: {
            cours: CoursResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cours'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

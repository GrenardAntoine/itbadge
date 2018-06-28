import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Badgeage } from 'app/shared/model/badgeage.model';
import { BadgeageService } from './badgeage.service';
import { BadgeageComponent } from './badgeage.component';
import { BadgeageDetailComponent } from './badgeage-detail.component';
import { BadgeageUpdateComponent } from './badgeage-update.component';
import { BadgeageDeletePopupComponent } from './badgeage-delete-dialog.component';
import { IBadgeage } from 'app/shared/model/badgeage.model';

@Injectable({ providedIn: 'root' })
export class BadgeageResolve implements Resolve<IBadgeage> {
    constructor(private service: BadgeageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((badgeage: HttpResponse<Badgeage>) => badgeage.body);
        }
        return Observable.of(new Badgeage());
    }
}

export const badgeageRoute: Routes = [
    {
        path: 'badgeage',
        component: BadgeageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Badgeages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badgeage/:id/view',
        component: BadgeageDetailComponent,
        resolve: {
            badgeage: BadgeageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Badgeages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badgeage/new',
        component: BadgeageUpdateComponent,
        resolve: {
            badgeage: BadgeageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Badgeages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badgeage/:id/edit',
        component: BadgeageUpdateComponent,
        resolve: {
            badgeage: BadgeageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Badgeages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const badgeagePopupRoute: Routes = [
    {
        path: 'badgeage/:id/delete',
        component: BadgeageDeletePopupComponent,
        resolve: {
            badgeage: BadgeageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Badgeages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

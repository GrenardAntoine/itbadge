import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Groupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';
import { GroupeComponent } from './groupe.component';
import { GroupeDetailComponent } from './groupe-detail.component';
import { GroupeUpdateComponent } from './groupe-update.component';
import { GroupeDeletePopupComponent } from './groupe-delete-dialog.component';
import { IGroupe } from 'app/shared/model/groupe.model';

@Injectable({ providedIn: 'root' })
export class GroupeResolve implements Resolve<IGroupe> {
    constructor(private service: GroupeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((groupe: HttpResponse<Groupe>) => groupe.body);
        }
        return Observable.of(new Groupe());
    }
}

export const groupeRoute: Routes = [
    {
        path: 'groupe',
        component: GroupeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Groupes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'groupe/:id/view',
        component: GroupeDetailComponent,
        resolve: {
            groupe: GroupeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groupes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'groupe/new',
        component: GroupeUpdateComponent,
        resolve: {
            groupe: GroupeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groupes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'groupe/:id/edit',
        component: GroupeUpdateComponent,
        resolve: {
            groupe: GroupeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groupes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupePopupRoute: Routes = [
    {
        path: 'groupe/:id/delete',
        component: GroupeDeletePopupComponent,
        resolve: {
            groupe: GroupeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Groupes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Utilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';
import { UtilisateurComponent } from './utilisateur.component';
import { UtilisateurDetailComponent } from './utilisateur-detail.component';
import { UtilisateurUpdateComponent } from './utilisateur-update.component';
import { UtilisateurDeletePopupComponent } from './utilisateur-delete-dialog.component';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

@Injectable({ providedIn: 'root' })
export class UtilisateurResolve implements Resolve<IUtilisateur> {
    constructor(private service: UtilisateurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((utilisateur: HttpResponse<Utilisateur>) => utilisateur.body);
        }
        return Observable.of(new Utilisateur());
    }
}

export const utilisateurRoute: Routes = [
    {
        path: 'utilisateur',
        component: UtilisateurComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Utilisateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'utilisateur/:id/view',
        component: UtilisateurDetailComponent,
        resolve: {
            utilisateur: UtilisateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Utilisateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'utilisateur/new',
        component: UtilisateurUpdateComponent,
        resolve: {
            utilisateur: UtilisateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Utilisateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'utilisateur/:id/edit',
        component: UtilisateurUpdateComponent,
        resolve: {
            utilisateur: UtilisateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Utilisateurs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const utilisateurPopupRoute: Routes = [
    {
        path: 'utilisateur/:id/delete',
        component: UtilisateurDeletePopupComponent,
        resolve: {
            utilisateur: UtilisateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Utilisateurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

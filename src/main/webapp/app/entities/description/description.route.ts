import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Description } from 'app/shared/model/description.model';
import { DescriptionService } from './description.service';
import { DescriptionComponent } from './description.component';
import { DescriptionDetailComponent } from './description-detail.component';
import { DescriptionUpdateComponent } from './description-update.component';
import { DescriptionDeletePopupComponent } from './description-delete-dialog.component';
import { IDescription } from 'app/shared/model/description.model';

@Injectable({ providedIn: 'root' })
export class DescriptionResolve implements Resolve<IDescription> {
    constructor(private service: DescriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((description: HttpResponse<Description>) => description.body);
        }
        return Observable.of(new Description());
    }
}

export const descriptionRoute: Routes = [
    {
        path: 'description',
        component: DescriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Descriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'description/:id/view',
        component: DescriptionDetailComponent,
        resolve: {
            description: DescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Descriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'description/new',
        component: DescriptionUpdateComponent,
        resolve: {
            description: DescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Descriptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'description/:id/edit',
        component: DescriptionUpdateComponent,
        resolve: {
            description: DescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Descriptions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const descriptionPopupRoute: Routes = [
    {
        path: 'description/:id/delete',
        component: DescriptionDeletePopupComponent,
        resolve: {
            description: DescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Descriptions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

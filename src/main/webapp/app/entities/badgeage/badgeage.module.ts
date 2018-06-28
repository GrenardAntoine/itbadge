import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItbadgeSharedModule } from 'app/shared';
import {
    BadgeageComponent,
    BadgeageDetailComponent,
    BadgeageUpdateComponent,
    BadgeageDeletePopupComponent,
    BadgeageDeleteDialogComponent,
    badgeageRoute,
    badgeagePopupRoute
} from './';

const ENTITY_STATES = [...badgeageRoute, ...badgeagePopupRoute];

@NgModule({
    imports: [ItbadgeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BadgeageComponent,
        BadgeageDetailComponent,
        BadgeageUpdateComponent,
        BadgeageDeleteDialogComponent,
        BadgeageDeletePopupComponent
    ],
    entryComponents: [BadgeageComponent, BadgeageUpdateComponent, BadgeageDeleteDialogComponent, BadgeageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItbadgeBadgeageModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItbadgeSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeService } from './';
import { HomeComponent } from './home.component';

@NgModule({
    imports: [ItbadgeSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [HomeService]
})
export class ItbadgeHomeModule {}

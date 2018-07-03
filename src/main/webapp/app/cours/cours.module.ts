import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItbadgeSharedModule } from 'app/shared';
import { CoursComponent } from './cours.component';
import { COURS_ROUTE } from './cours.route';

@NgModule({
    imports: [ItbadgeSharedModule, RouterModule.forChild([COURS_ROUTE])],
    declarations: [CoursComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItbadgeCoursModule {}

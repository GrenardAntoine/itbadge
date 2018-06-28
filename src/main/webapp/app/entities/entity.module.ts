import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ItbadgeDescriptionModule } from './description/description.module';
import { ItbadgeGroupeModule } from './groupe/groupe.module';
import { ItbadgeUtilisateurModule } from './utilisateur/utilisateur.module';
import { ItbadgeBadgeageModule } from './badgeage/badgeage.module';
import { ItbadgeCoursModule } from './cours/cours.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ItbadgeDescriptionModule,
        ItbadgeGroupeModule,
        ItbadgeUtilisateurModule,
        ItbadgeBadgeageModule,
        ItbadgeCoursModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItbadgeEntityModule {}

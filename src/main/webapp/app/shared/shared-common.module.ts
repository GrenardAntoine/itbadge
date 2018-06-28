import { NgModule } from '@angular/core';

import { ItbadgeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ItbadgeSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ItbadgeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ItbadgeSharedCommonModule {}

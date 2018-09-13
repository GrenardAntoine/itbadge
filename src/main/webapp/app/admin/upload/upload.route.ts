import { Route } from '@angular/router';

import { JhiUploadComponent } from './upload.component';

export const uploadRoute: Route = {
    path: 'jhi-upload',
    component: JhiUploadComponent,
    data: {
        pageTitle: 'Upload'
    }
};

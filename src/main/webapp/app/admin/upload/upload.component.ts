import { Component, OnInit } from '@angular/core';

import { JhiUploadService } from './upload.service';

@Component({
    selector: 'jhi-upload',
    templateUrl: './upload.component.html'
})
export class JhiUploadComponent implements OnInit {
    private newFile: File;

    constructor(private uploadService: JhiUploadService) {}

    ngOnInit() {}

    setFile(files: FileList) {
        this.newFile = files.item(0);
    }

    upload() {
        this.uploadService.sendFile(this.newFile).subscribe(
            res => {
                console.log('Envoyé');
            },
            err => {
                console.log('Erreur : ' + err.message);
                console.log(err);
            }
        );
    }
}

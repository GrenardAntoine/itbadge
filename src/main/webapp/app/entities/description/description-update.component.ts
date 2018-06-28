import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDescription } from 'app/shared/model/description.model';
import { DescriptionService } from './description.service';

@Component({
    selector: 'jhi-description-update',
    templateUrl: './description-update.component.html'
})
export class DescriptionUpdateComponent implements OnInit {
    private _description: IDescription;
    isSaving: boolean;

    constructor(private descriptionService: DescriptionService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ description }) => {
            this.description = description;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.description.id !== undefined) {
            this.subscribeToSaveResponse(this.descriptionService.update(this.description));
        } else {
            this.subscribeToSaveResponse(this.descriptionService.create(this.description));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDescription>>) {
        result.subscribe((res: HttpResponse<IDescription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get description() {
        return this._description;
    }

    set description(description: IDescription) {
        this._description = description;
    }
}

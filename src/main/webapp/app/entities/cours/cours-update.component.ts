import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from './cours.service';

@Component({
    selector: 'jhi-cours-update',
    templateUrl: './cours-update.component.html'
})
export class CoursUpdateComponent implements OnInit {
    private _cours: ICours;
    isSaving: boolean;
    dateDebut: string;
    dateFin: string;

    constructor(private coursService: CoursService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cours }) => {
            this.cours = cours;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cours.dateDebut = moment(this.dateDebut, DATE_TIME_FORMAT);
        this.cours.dateFin = moment(this.dateFin, DATE_TIME_FORMAT);
        if (this.cours.id !== undefined) {
            this.subscribeToSaveResponse(this.coursService.update(this.cours));
        } else {
            this.subscribeToSaveResponse(this.coursService.create(this.cours));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICours>>) {
        result.subscribe((res: HttpResponse<ICours>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get cours() {
        return this._cours;
    }

    set cours(cours: ICours) {
        this._cours = cours;
        this.dateDebut = moment(cours.dateDebut).format(DATE_TIME_FORMAT);
        this.dateFin = moment(cours.dateFin).format(DATE_TIME_FORMAT);
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBadgeage } from 'app/shared/model/badgeage.model';
import { BadgeageService } from './badgeage.service';

@Component({
    selector: 'jhi-badgeage-update',
    templateUrl: './badgeage-update.component.html'
})
export class BadgeageUpdateComponent implements OnInit {
    private _badgeage: IBadgeage;
    isSaving: boolean;
    currentDateDp: any;
    badgeageEleve: string;
    badgeageCorrige: string;

    constructor(private badgeageService: BadgeageService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ badgeage }) => {
            this.badgeage = badgeage;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.badgeage.badgeageEleve = moment(this.badgeageEleve, DATE_TIME_FORMAT);
        this.badgeage.badgeageCorrige = moment(this.badgeageCorrige, DATE_TIME_FORMAT);
        if (this.badgeage.id !== undefined) {
            this.subscribeToSaveResponse(this.badgeageService.update(this.badgeage));
        } else {
            this.subscribeToSaveResponse(this.badgeageService.create(this.badgeage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBadgeage>>) {
        result.subscribe((res: HttpResponse<IBadgeage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get badgeage() {
        return this._badgeage;
    }

    set badgeage(badgeage: IBadgeage) {
        this._badgeage = badgeage;
        this.badgeageEleve = moment(badgeage.badgeageEleve).format(DATE_TIME_FORMAT);
        this.badgeageCorrige = moment(badgeage.badgeageCorrige).format(DATE_TIME_FORMAT);
    }
}

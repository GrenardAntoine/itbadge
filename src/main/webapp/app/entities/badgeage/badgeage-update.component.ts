import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IBadgeage } from 'app/shared/model/badgeage.model';
import { BadgeageService } from './badgeage.service';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur';

@Component({
    selector: 'jhi-badgeage-update',
    templateUrl: './badgeage-update.component.html'
})
export class BadgeageUpdateComponent implements OnInit {
    private _badgeage: IBadgeage;
    isSaving: boolean;

    utilisateurs: IUtilisateur[];
    currentDateDp: any;
    badgeageEleve: string;
    badgeageCorrige: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private badgeageService: BadgeageService,
        private utilisateurService: UtilisateurService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ badgeage }) => {
            this.badgeage = badgeage;
        });
        this.utilisateurService.query().subscribe(
            (res: HttpResponse<IUtilisateur[]>) => {
                this.utilisateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUtilisateurById(index: number, item: IUtilisateur) {
        return item.id;
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

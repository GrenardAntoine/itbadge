import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from './cours.service';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur';
import { IDescription } from 'app/shared/model/description.model';
import { DescriptionService } from 'app/entities/description';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe';

@Component({
    selector: 'jhi-cours-update',
    templateUrl: './cours-update.component.html'
})
export class CoursUpdateComponent implements OnInit {
    private _cours: ICours;
    isSaving: boolean;

    utilisateurs: IUtilisateur[];

    descriptions: IDescription[];

    groupes: IGroupe[];
    dateDebut: string;
    dateFin: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private coursService: CoursService,
        private utilisateurService: UtilisateurService,
        private descriptionService: DescriptionService,
        private groupeService: GroupeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cours }) => {
            this.cours = cours;
        });
        this.utilisateurService.query().subscribe(
            (res: HttpResponse<IUtilisateur[]>) => {
                this.utilisateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.descriptionService.query().subscribe(
            (res: HttpResponse<IDescription[]>) => {
                this.descriptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.groupeService.query().subscribe(
            (res: HttpResponse<IGroupe[]>) => {
                this.groupes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUtilisateurById(index: number, item: IUtilisateur) {
        return item.id;
    }

    trackDescriptionById(index: number, item: IDescription) {
        return item.id;
    }

    trackGroupeById(index: number, item: IGroupe) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
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

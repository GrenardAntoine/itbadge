import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe';
import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from 'app/entities/cours';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-utilisateur-update',
    templateUrl: './utilisateur-update.component.html'
})
export class UtilisateurUpdateComponent implements OnInit {
    private _utilisateur: IUtilisateur;
    isSaving: boolean;

    groupes: IGroupe[];

    cours: ICours[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private utilisateurService: UtilisateurService,
        private groupeService: GroupeService,
        private coursService: CoursService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ utilisateur }) => {
            this.utilisateur = utilisateur;
        });
        this.groupeService.query().subscribe(
            (res: HttpResponse<IGroupe[]>) => {
                this.groupes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.coursService.query().subscribe(
            (res: HttpResponse<ICours[]>) => {
                this.cours = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.utilisateur.id !== undefined) {
            this.subscribeToSaveResponse(this.utilisateurService.update(this.utilisateur));
        } else {
            this.subscribeToSaveResponse(this.utilisateurService.create(this.utilisateur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateur>>) {
        result.subscribe((res: HttpResponse<IUtilisateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGroupeById(index: number, item: IGroupe) {
        return item.id;
    }

    trackCoursById(index: number, item: ICours) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
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
    get utilisateur() {
        return this._utilisateur;
    }

    set utilisateur(utilisateur: IUtilisateur) {
        this._utilisateur = utilisateur;
    }
}

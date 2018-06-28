import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';
import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from 'app/entities/cours';

@Component({
    selector: 'jhi-groupe-update',
    templateUrl: './groupe-update.component.html'
})
export class GroupeUpdateComponent implements OnInit {
    private _groupe: IGroupe;
    isSaving: boolean;

    cours: ICours[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private groupeService: GroupeService,
        private coursService: CoursService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupe }) => {
            this.groupe = groupe;
        });
        this.coursService.query().subscribe(
            (res: HttpResponse<ICours[]>) => {
                this.cours = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupe.id !== undefined) {
            this.subscribeToSaveResponse(this.groupeService.update(this.groupe));
        } else {
            this.subscribeToSaveResponse(this.groupeService.create(this.groupe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>) {
        result.subscribe((res: HttpResponse<IGroupe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCoursById(index: number, item: ICours) {
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
    get groupe() {
        return this._groupe;
    }

    set groupe(groupe: IGroupe) {
        this._groupe = groupe;
    }
}

import { ICours } from '../shared/model/cours.model';
import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { Principal } from '../core/auth/principal.service';
import { CoursService } from '../entities/cours/cours.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';
import { IDescription } from '../shared/model/description.model';
import { IUtilisateur } from '../shared/model/utilisateur.model';

@Component({
    selector: 'jhi-cours',
    templateUrl: './cours.component.html',
    styleUrls: ['cours.scss']
})
export class CoursComponent implements OnInit {
    account: Account = null;
    listCours: ICours[] = null;
    listCoursName: ICours[] = null;

    cours: ICours = null;
    description: IDescription = null;
    listEleve: IUtilisateur = null;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private coursService: CoursService
    ) {}

    ngOnInit() {
        this.coursService
            .query()
            .subscribe((res: HttpResponse<ICours[]>) => (this.listCours = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }
}

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
import { GroupeService } from '../entities/groupe/groupe.service';

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
    listCurrentEleve: IUtilisateur[] = [];

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private coursService: CoursService,
        private groupeService: GroupeService
    ) {}

    ngOnInit() {
        this.coursService
            .query()
            .subscribe((res: HttpResponse<ICours[]>) => (this.listCours = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }

    // TODO : Implement when PAF has done listCoursName
    changeCoursName(nom) {}

    // TODO : Implement when PAF has done listCoursName
    changeCoursDate(id) {
        this.cours = null;
        this.listCurrentEleve = [];

        this.coursService.find(id).subscribe(res => {
            this.cours = res.body;
            let dateCours = this.cours.dateDebut.format('YYYY-MM-DD');

            this.cours.listGroupes.forEach(groupe => {
                this.groupeService.findBadgeageGroupe(groupe.id, dateCours).subscribe(res => {
                    this.listCurrentEleve = this.listCurrentEleve.concat(res.body.listEleves);
                });
            });
        });
    }
}

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
import { forEach } from '@angular/router/src/utils/collection';
import { FormGroup } from '@angular/forms';
import { UtilisateurService } from '../entities/utilisateur/utilisateur.service';
import { BadgeageService } from '../entities/badgeage/badgeage.service';

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

    nbBadgeageMatin: number = 0;
    nbBadgeageAprem: number = 0;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private coursService: CoursService,
        private groupeService: GroupeService,
        private badgeageService: BadgeageService
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
        this.nbBadgeageMatin = 0;
        this.nbBadgeageAprem = 0;

        this.coursService.find(id).subscribe(res => {
            this.cours = res.body;
            let dateCours = this.cours.dateDebut.format('YYYY-MM-DD');

            this.cours.listGroupes.forEach(groupe => {
                this.groupeService.findBadgeageGroupe(groupe.id, dateCours).subscribe(res => {
                    this.listCurrentEleve = this.listCurrentEleve.concat(res.body.listEleves);
                    this.countBadgeage();
                });
            });
        });
    }

    countBadgeage() {
        let dateMidi = this.cours.dateDebut;
        dateMidi.set({ hour: 12, minute: 0, second: 0, millisecond: 0 });
        console.log(dateMidi);

        this.listCurrentEleve.forEach(eleve => {
            eleve.listBageages.forEach(badgeage => {
                if (badgeage.badgeageEleve < dateMidi) this.nbBadgeageMatin++;
                else this.nbBadgeageAprem++;
            });
        });
    }

    save() {
        this.listCurrentEleve.forEach(eleve => {
            eleve.listBageages.forEach(badgeage => {
                this.badgeageService.update(badgeage).subscribe();
            });
        });
    }

    changeHourMinute(badgeageCorrige, event) {
        if (event.indexOf(':') === 2 && event.length === 5) {
            let hour = event.substr(0, 2);
            let minute = event.substr(3, 5);
            badgeageCorrige.set({ hour: hour, minute: minute });
        }
    }
}

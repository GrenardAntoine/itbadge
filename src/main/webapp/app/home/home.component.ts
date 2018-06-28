import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import * as moment from 'moment';

import { Principal, Account } from 'app/core';
import { Router } from '@angular/router';

import { Cours } from '../shared/model/cours.model';
import { Utilisateur } from '../shared/model/utilisateur.model';
import { Badgeage } from '../shared/model/badgeage.model';
import { HomeService } from './home.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account = null;
    listEleveBadgeage: Utilisateur[] = [];
    cours: Cours = null;
    listBadgeage: Badgeage[] = [];

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private homeService: HomeService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;

            if (this.account.authorities.includes('ROLE_ADMIN')) {
            } else if (this.account.authorities.includes('ROLE_PROFESSEUR')) {
                // this.homeService.getListEleveBadgeage().subscribe((res) => {
                //     this.listEleveBadgeage = res;
                // });
            } else if (this.account.authorities.includes('ROLE_USER')) {
                // this.homeService.getCurrentCours().subscribe((res) => {
                //     this.cours = res;
                // });
                this.cours = new Cours();
                this.cours.nom = 'Java';
                // this.homeService.getListBadgeage().subscribe((res) => {
                //     this.listBadgeage = res;
                // });
                const badgeage1 = new Badgeage();
                badgeage1.badgeageEleve = moment('2018-06-26T09:00:00.000Z');
                badgeage1.badgeageCorrige = moment('2018-06-26T09:00:00.000Z');

                const badgeage2 = new Badgeage();
                badgeage2.badgeageEleve = moment('2018-06-26T09:00:00.000Z');
                badgeage2.badgeageCorrige = moment('2018-06-26T09:00:00.000Z');

                this.listBadgeage.push(badgeage1);
                this.listBadgeage.push(badgeage2);
            }
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isEleve() {
        if (this.account !== null) {
            return !this.account.authorities.includes('ROLE_PROFESSEUR') && !this.account.authorities.includes('ROLE_ADMIN');
        }
        return false;
    }

    isProfesseur() {
        if (this.account !== null) {
            return this.account.authorities.includes('ROLE_PROFESSEUR');
        }
        return false;
    }

    login() {
        this.router.navigate(['login']);
    }
}

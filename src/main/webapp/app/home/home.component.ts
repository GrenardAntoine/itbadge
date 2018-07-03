import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import * as moment from 'moment';

import { Principal, Account } from 'app/core';
import { Router } from '@angular/router';

import { Cours, ICours } from '../shared/model/cours.model';
import { Utilisateur } from '../shared/model/utilisateur.model';
import { Badgeage } from '../shared/model/badgeage.model';
import { HomeService } from './home.service';
import { CoursService } from '../entities/cours/cours.service';
import { BadgeageService } from '../entities/badgeage/badgeage.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account = null;
    listEleveBadgeage: Utilisateur[] = [];
    cours: ICours = null;
    listBadgeage: Badgeage[] = [];

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private coursService: CoursService,
        private badgeageService: BadgeageService
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
                this.coursService.getCurrentCours().subscribe(res => {
                    this.cours = res.body;
                });

                this.badgeageService.getListTodayBadgeageForStudent().subscribe(res => {
                    this.listBadgeage = res.body;
                    console.log(this.listBadgeage);
                });
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

    badger() {
        this.badgeageService.badger().subscribe(res => {
            this.badgeageService.getListTodayBadgeageForStudent().subscribe(res => {
                this.listBadgeage = res.body;
                console.log(this.listBadgeage);
            });
        });
    }
}

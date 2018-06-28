import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBadgeage } from 'app/shared/model/badgeage.model';

@Component({
    selector: 'jhi-badgeage-detail',
    templateUrl: './badgeage-detail.component.html'
})
export class BadgeageDetailComponent implements OnInit {
    badgeage: IBadgeage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ badgeage }) => {
            this.badgeage = badgeage;
        });
    }

    previousState() {
        window.history.back();
    }
}

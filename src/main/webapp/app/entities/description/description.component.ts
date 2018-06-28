import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDescription } from 'app/shared/model/description.model';
import { Principal } from 'app/core';
import { DescriptionService } from './description.service';

@Component({
    selector: 'jhi-description',
    templateUrl: './description.component.html'
})
export class DescriptionComponent implements OnInit, OnDestroy {
    descriptions: IDescription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private descriptionService: DescriptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.descriptionService.query().subscribe(
            (res: HttpResponse<IDescription[]>) => {
                this.descriptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDescriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDescription) {
        return item.id;
    }

    registerChangeInDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('descriptionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBadgeage } from 'app/shared/model/badgeage.model';
import { BadgeageService } from './badgeage.service';

@Component({
    selector: 'jhi-badgeage-delete-dialog',
    templateUrl: './badgeage-delete-dialog.component.html'
})
export class BadgeageDeleteDialogComponent {
    badgeage: IBadgeage;

    constructor(private badgeageService: BadgeageService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.badgeageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'badgeageListModification',
                content: 'Deleted an badgeage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-badgeage-delete-popup',
    template: ''
})
export class BadgeageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ badgeage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BadgeageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.badgeage = badgeage;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescription } from 'app/shared/model/description.model';
import { DescriptionService } from './description.service';

@Component({
    selector: 'jhi-description-delete-dialog',
    templateUrl: './description-delete-dialog.component.html'
})
export class DescriptionDeleteDialogComponent {
    description: IDescription;

    constructor(
        private descriptionService: DescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.descriptionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'descriptionListModification',
                content: 'Deleted an description'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-description-delete-popup',
    template: ''
})
export class DescriptionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ description }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DescriptionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.description = description;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ItbadgeTestModule } from '../../../test.module';
import { BadgeageDeleteDialogComponent } from 'app/entities/badgeage/badgeage-delete-dialog.component';
import { BadgeageService } from 'app/entities/badgeage/badgeage.service';

describe('Component Tests', () => {
    describe('Badgeage Management Delete Component', () => {
        let comp: BadgeageDeleteDialogComponent;
        let fixture: ComponentFixture<BadgeageDeleteDialogComponent>;
        let service: BadgeageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ItbadgeTestModule],
                declarations: [BadgeageDeleteDialogComponent]
            })
                .overrideTemplate(BadgeageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BadgeageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BadgeageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

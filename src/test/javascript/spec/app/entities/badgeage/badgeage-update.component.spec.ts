/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ItbadgeTestModule } from '../../../test.module';
import { BadgeageUpdateComponent } from 'app/entities/badgeage/badgeage-update.component';
import { BadgeageService } from 'app/entities/badgeage/badgeage.service';
import { Badgeage } from 'app/shared/model/badgeage.model';

describe('Component Tests', () => {
    describe('Badgeage Management Update Component', () => {
        let comp: BadgeageUpdateComponent;
        let fixture: ComponentFixture<BadgeageUpdateComponent>;
        let service: BadgeageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ItbadgeTestModule],
                declarations: [BadgeageUpdateComponent]
            })
                .overrideTemplate(BadgeageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BadgeageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BadgeageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Badgeage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.badgeage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Badgeage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.badgeage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

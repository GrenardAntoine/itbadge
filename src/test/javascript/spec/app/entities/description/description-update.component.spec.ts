/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ItbadgeTestModule } from '../../../test.module';
import { DescriptionUpdateComponent } from 'app/entities/description/description-update.component';
import { DescriptionService } from 'app/entities/description/description.service';
import { Description } from 'app/shared/model/description.model';

describe('Component Tests', () => {
    describe('Description Management Update Component', () => {
        let comp: DescriptionUpdateComponent;
        let fixture: ComponentFixture<DescriptionUpdateComponent>;
        let service: DescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ItbadgeTestModule],
                declarations: [DescriptionUpdateComponent]
            })
                .overrideTemplate(DescriptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescriptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescriptionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Description(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.description = entity;
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
                    const entity = new Description();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.description = entity;
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

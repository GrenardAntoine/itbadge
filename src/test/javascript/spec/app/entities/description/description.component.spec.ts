/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ItbadgeTestModule } from '../../../test.module';
import { DescriptionComponent } from 'app/entities/description/description.component';
import { DescriptionService } from 'app/entities/description/description.service';
import { Description } from 'app/shared/model/description.model';

describe('Component Tests', () => {
    describe('Description Management Component', () => {
        let comp: DescriptionComponent;
        let fixture: ComponentFixture<DescriptionComponent>;
        let service: DescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ItbadgeTestModule],
                declarations: [DescriptionComponent],
                providers: []
            })
                .overrideTemplate(DescriptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescriptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Description(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.descriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

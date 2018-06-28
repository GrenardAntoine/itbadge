/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ItbadgeTestModule } from '../../../test.module';
import { BadgeageDetailComponent } from 'app/entities/badgeage/badgeage-detail.component';
import { Badgeage } from 'app/shared/model/badgeage.model';

describe('Component Tests', () => {
    describe('Badgeage Management Detail Component', () => {
        let comp: BadgeageDetailComponent;
        let fixture: ComponentFixture<BadgeageDetailComponent>;
        const route = ({ data: of({ badgeage: new Badgeage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ItbadgeTestModule],
                declarations: [BadgeageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BadgeageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BadgeageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.badgeage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

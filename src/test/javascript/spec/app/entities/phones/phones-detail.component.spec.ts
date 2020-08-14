import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpringTestModule } from '../../../test.module';
import { PhonesDetailComponent } from 'app/entities/phones/phones-detail.component';
import { Phones } from 'app/shared/model/phones.model';

describe('Component Tests', () => {
  describe('Phones Management Detail Component', () => {
    let comp: PhonesDetailComponent;
    let fixture: ComponentFixture<PhonesDetailComponent>;
    const route = ({ data: of({ phones: new Phones(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpringTestModule],
        declarations: [PhonesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhonesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhonesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phones on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phones).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpringTestModule } from '../../../test.module';
import { PhonesUpdateComponent } from 'app/entities/phones/phones-update.component';
import { PhonesService } from 'app/entities/phones/phones.service';
import { Phones } from 'app/shared/model/phones.model';

describe('Component Tests', () => {
  describe('Phones Management Update Component', () => {
    let comp: PhonesUpdateComponent;
    let fixture: ComponentFixture<PhonesUpdateComponent>;
    let service: PhonesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpringTestModule],
        declarations: [PhonesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhonesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhonesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhonesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Phones(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Phones();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhones } from 'app/shared/model/phones.model';
import { PhonesService } from './phones.service';

@Component({
  templateUrl: './phones-delete-dialog.component.html',
})
export class PhonesDeleteDialogComponent {
  phones?: IPhones;

  constructor(protected phonesService: PhonesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phonesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('phonesListModification');
      this.activeModal.close();
    });
  }
}

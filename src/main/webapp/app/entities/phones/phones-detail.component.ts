import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhones } from 'app/shared/model/phones.model';

@Component({
  selector: 'jhi-phones-detail',
  templateUrl: './phones-detail.component.html',
})
export class PhonesDetailComponent implements OnInit {
  phones: IPhones | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phones }) => (this.phones = phones));
  }

  previousState(): void {
    window.history.back();
  }
}

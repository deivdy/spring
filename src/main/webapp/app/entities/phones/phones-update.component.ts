import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhones, Phones } from 'app/shared/model/phones.model';
import { PhonesService } from './phones.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-phones-update',
  templateUrl: './phones-update.component.html',
})
export class PhonesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.minLength(8)]],
    ddd: [null, [Validators.required, Validators.minLength(2)]],
    user: [],
  });

  constructor(
    protected phonesService: PhonesService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phones }) => {
      this.updateForm(phones);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(phones: IPhones): void {
    this.editForm.patchValue({
      id: phones.id,
      number: phones.number,
      ddd: phones.ddd,
      user: phones.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phones = this.createFromForm();
    if (phones.id !== undefined) {
      this.subscribeToSaveResponse(this.phonesService.update(phones));
    } else {
      this.subscribeToSaveResponse(this.phonesService.create(phones));
    }
  }

  private createFromForm(): IPhones {
    return {
      ...new Phones(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      ddd: this.editForm.get(['ddd'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhones>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhones, Phones } from 'app/shared/model/phones.model';
import { PhonesService } from './phones.service';
import { PhonesComponent } from './phones.component';
import { PhonesDetailComponent } from './phones-detail.component';
import { PhonesUpdateComponent } from './phones-update.component';

@Injectable({ providedIn: 'root' })
export class PhonesResolve implements Resolve<IPhones> {
  constructor(private service: PhonesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhones> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((phones: HttpResponse<Phones>) => {
          if (phones.body) {
            return of(phones.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Phones());
  }
}

export const phonesRoute: Routes = [
  {
    path: '',
    component: PhonesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Phones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhonesDetailComponent,
    resolve: {
      phones: PhonesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Phones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhonesUpdateComponent,
    resolve: {
      phones: PhonesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Phones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhonesUpdateComponent,
    resolve: {
      phones: PhonesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Phones',
    },
    canActivate: [UserRouteAccessService],
  },
];

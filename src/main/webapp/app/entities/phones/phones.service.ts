import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhones } from 'app/shared/model/phones.model';

type EntityResponseType = HttpResponse<IPhones>;
type EntityArrayResponseType = HttpResponse<IPhones[]>;

@Injectable({ providedIn: 'root' })
export class PhonesService {
  public resourceUrl = SERVER_API_URL + 'api/phones';

  constructor(protected http: HttpClient) {}

  create(phones: IPhones): Observable<EntityResponseType> {
    return this.http.post<IPhones>(this.resourceUrl, phones, { observe: 'response' });
  }

  update(phones: IPhones): Observable<EntityResponseType> {
    return this.http.put<IPhones>(this.resourceUrl, phones, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhones>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhones[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

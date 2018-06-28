import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDescription } from 'app/shared/model/description.model';

type EntityResponseType = HttpResponse<IDescription>;
type EntityArrayResponseType = HttpResponse<IDescription[]>;

@Injectable({ providedIn: 'root' })
export class DescriptionService {
    private resourceUrl = SERVER_API_URL + 'api/descriptions';

    constructor(private http: HttpClient) {}

    create(description: IDescription): Observable<EntityResponseType> {
        return this.http.post<IDescription>(this.resourceUrl, description, { observe: 'response' });
    }

    update(description: IDescription): Observable<EntityResponseType> {
        return this.http.put<IDescription>(this.resourceUrl, description, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDescription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDescription[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

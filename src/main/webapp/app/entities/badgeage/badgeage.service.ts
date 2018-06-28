import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBadgeage } from 'app/shared/model/badgeage.model';

type EntityResponseType = HttpResponse<IBadgeage>;
type EntityArrayResponseType = HttpResponse<IBadgeage[]>;

@Injectable({ providedIn: 'root' })
export class BadgeageService {
    private resourceUrl = SERVER_API_URL + 'api/badgeages';

    constructor(private http: HttpClient) {}

    create(badgeage: IBadgeage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(badgeage);
        return this.http
            .post<IBadgeage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(badgeage: IBadgeage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(badgeage);
        return this.http
            .put<IBadgeage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBadgeage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBadgeage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(badgeage: IBadgeage): IBadgeage {
        const copy: IBadgeage = Object.assign({}, badgeage, {
            currentDate: badgeage.currentDate != null && badgeage.currentDate.isValid() ? badgeage.currentDate.format(DATE_FORMAT) : null,
            badgeageEleve: badgeage.badgeageEleve != null && badgeage.badgeageEleve.isValid() ? badgeage.badgeageEleve.toJSON() : null,
            badgeageCorrige:
                badgeage.badgeageCorrige != null && badgeage.badgeageCorrige.isValid() ? badgeage.badgeageCorrige.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.currentDate = res.body.currentDate != null ? moment(res.body.currentDate) : null;
        res.body.badgeageEleve = res.body.badgeageEleve != null ? moment(res.body.badgeageEleve) : null;
        res.body.badgeageCorrige = res.body.badgeageCorrige != null ? moment(res.body.badgeageCorrige) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((badgeage: IBadgeage) => {
            badgeage.currentDate = badgeage.currentDate != null ? moment(badgeage.currentDate) : null;
            badgeage.badgeageEleve = badgeage.badgeageEleve != null ? moment(badgeage.badgeageEleve) : null;
            badgeage.badgeageCorrige = badgeage.badgeageCorrige != null ? moment(badgeage.badgeageCorrige) : null;
        });
        return res;
    }
}

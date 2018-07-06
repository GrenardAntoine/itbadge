import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICours } from 'app/shared/model/cours.model';

type EntityResponseType = HttpResponse<ICours>;
type EntityArrayResponseType = HttpResponse<ICours[]>;

@Injectable({ providedIn: 'root' })
export class CoursService {
    private resourceUrl = SERVER_API_URL + 'api/cours';

    constructor(private http: HttpClient) {}

    create(cours: ICours): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cours);
        return this.http
            .post<ICours>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(cours: ICours): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cours);
        return this.http
            .put<ICours>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICours>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICours[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(cours: ICours): ICours {
        const copy: ICours = Object.assign({}, cours, {
            dateDebut: cours.dateDebut != null && cours.dateDebut.isValid() ? cours.dateDebut.toJSON() : null,
            dateFin: cours.dateFin != null && cours.dateFin.isValid() ? cours.dateFin.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
        res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((cours: ICours) => {
            cours.dateDebut = cours.dateDebut != null ? moment(cours.dateDebut) : null;
            cours.dateFin = cours.dateFin != null ? moment(cours.dateFin) : null;
        });
        return res;
    }

    getCurrentCours(): Observable<EntityResponseType> {
        return this.http
            .get<ICours>(`${this.resourceUrl}/current`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    getListCoursByCurrentProfesseur(): Observable<EntityArrayResponseType> {
        return this.http
            .get<ICours[]>(this.resourceUrl + '/currentProfesseur', { observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }
}

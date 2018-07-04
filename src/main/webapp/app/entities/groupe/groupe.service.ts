import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupe } from 'app/shared/model/groupe.model';
import { IUtilisateur } from '../../shared/model/utilisateur.model';
import { IBadgeage } from '../../shared/model/badgeage.model';
import moment = require('moment');

type EntityResponseType = HttpResponse<IGroupe>;
type EntityArrayResponseType = HttpResponse<IGroupe[]>;

@Injectable({ providedIn: 'root' })
export class GroupeService {
    private resourceUrl = SERVER_API_URL + 'api/groupes';

    constructor(private http: HttpClient) {}

    create(groupe: IGroupe): Observable<EntityResponseType> {
        return this.http.post<IGroupe>(this.resourceUrl, groupe, { observe: 'response' });
    }

    update(groupe: IGroupe): Observable<EntityResponseType> {
        return this.http.put<IGroupe>(this.resourceUrl, groupe, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroupe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroupe[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.listEleves.forEach((utilisateur: IUtilisateur) => {
            utilisateur.listBageages.forEach((badgeage: IBadgeage) => {
                badgeage.currentDate = badgeage.currentDate != null ? moment(badgeage.currentDate) : null;
                badgeage.badgeageEleve = badgeage.badgeageEleve != null ? moment(badgeage.badgeageEleve) : null;
                badgeage.badgeageCorrige = badgeage.badgeageCorrige != null ? moment(badgeage.badgeageCorrige) : null;
            });
        });
        return res;
    }

    findBadgeageGroupe(id: number, date: string): Observable<EntityResponseType> {
        return this.http
            .get<IGroupe>(this.resourceUrl + '/badgeageGroupe/' + id + '/' + date, { observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }
}

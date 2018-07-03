import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';
import { ICours } from '../shared/model/cours.model';

@Injectable()
export class HomeService {
    constructor(private http: HttpClient) {}

    getCurrentCours(): Observable<ICours> {
        // TODO : voir quel url d'api pour récupérer ça
        return this.http.get<ICours>(SERVER_API_URL + '/cours/current', { observe: 'response' });
        /*
        return this.http.get(SERVER_API_URL + '/cours/current').map(res => {
            console.log('res:'+res);
            return res.json().results.map(item => {
                console.log('item:'+item);
                return new ICours(res.id, res.nom, res.dateDebut, res.dateFin, res.listProfesseurs, res.description, res.listGroupes);
            });
        });*/
    }

    getListBadgeage(): Observable<Badgeage[]> {
        // TODO : voir quel url d'api pour récupérer ça
        return this.http.get(SERVER_API_URL + 'cours').map(res => {
            return res.json().results.map(item => {
                return new ICours(res.id, res.nom, res.dateDebut, res.dateFin, res.listProfesseurs, res.description, res.listGroupes);
            });
        });
    }

    getListEleveBadgeage(): Observable<any> {
        // TODO : voir quel url d'api pour récupérer ça
        // return this.http.get(SERVER_API_URL + 'management/metrics');
        /*return Observable.of('[' +
         '{"utilisateur" : ' +
         '{ "nom" : "Grenard", "prenom" : "Antoine", "listBadgeages" : [ ' +
         '{"badgeage_eleve" : "09:00" , "badgeage_corrige" : "09:00"},' +
         '{"badgeage_eleve" : "17:00" , "badgeage_corrige" : "17:00"}]}},' +
         '{"utilisateur" : ' +
         '{ "nom" : "Guinart", "prenom" : "Michael", "listBadgeages" : [ ' +
         '{"badgeage_eleve" : "09:00" , "badgeage_corrige" : "09:00"},' +
         '{"badgeage_eleve" : "17:00" , "badgeage_corrige" : "17:00"}]}}]');*/
        return null;
    }
}

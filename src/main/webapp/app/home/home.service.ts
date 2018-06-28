import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';

@Injectable()
export class HomeService {
    constructor(private http: HttpClient) {}

    getCurrentCours(): Observable<any> {
        // TODO : voir quel url d'api pour récupérer ça
        // return this.http.get(SERVER_API_URL + 'management/metrics');
        /*return Observable.of('{"nom" : "Java"}');*/
        return null;
    }

    getListBadgeage(): Observable<any> {
        // TODO : voir quel url d'api pour récupérer ça
        // return this.http.get(SERVER_API_URL + 'management/metrics');
        /*return Observable.of('[{"badgeage_eleve" : "09:00" , "badgeage_corrige" : "09:00"},' +
         '{"badgeage_eleve" : "17:00" , "badgeage_corrige" : "17:00"}]');*/
        return null;
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

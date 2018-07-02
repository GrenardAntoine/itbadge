import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cours } from '../shared/model/cours.model';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../app.constants';

@Injectable()
export class CoursService {
    constructor(private http: HttpClient) {}

    getListCours(): Observable<Cours[]> {
        //TODO : Mettre la bonne requete ici
        return this.http.get(SERVER_API_URL + 'cours').map(res => {
            return res.json().results.map(item => {
                return new Cours(res.id, res.nom, res.dateDebut, res.dateFin, res.listProfesseurs, res.description, res.listGroupes);
            });
        });
    }
}

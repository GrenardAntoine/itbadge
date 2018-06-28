import { IUtilisateur } from 'app/shared/model//utilisateur.model';
import { ICours } from 'app/shared/model//cours.model';

export interface IGroupe {
    id?: number;
    nom?: string;
    listEleves?: IUtilisateur[];
    listCours?: ICours[];
}

export class Groupe implements IGroupe {
    constructor(public id?: number, public nom?: string, public listEleves?: IUtilisateur[], public listCours?: ICours[]) {}
}

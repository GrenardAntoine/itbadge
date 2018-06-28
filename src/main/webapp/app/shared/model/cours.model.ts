import { Moment } from 'moment';
import { IUtilisateur } from 'app/shared/model//utilisateur.model';
import { IDescription } from 'app/shared/model//description.model';
import { IGroupe } from 'app/shared/model//groupe.model';

export interface ICours {
    id?: number;
    nom?: string;
    dateDebut?: Moment;
    dateFin?: Moment;
    listProfesseurs?: IUtilisateur[];
    description?: IDescription;
    listGroupes?: IGroupe[];
}

export class Cours implements ICours {
    constructor(
        public id?: number,
        public nom?: string,
        public dateDebut?: Moment,
        public dateFin?: Moment,
        public listProfesseurs?: IUtilisateur[],
        public description?: IDescription,
        public listGroupes?: IGroupe[]
    ) {}
}

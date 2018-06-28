import { Moment } from 'moment';
import { IUtilisateur } from 'app/shared/model//utilisateur.model';

export interface IBadgeage {
    id?: number;
    currentDate?: Moment;
    badgeageEleve?: Moment;
    badgeageCorrige?: Moment;
    utilisateur?: IUtilisateur;
}

export class Badgeage implements IBadgeage {
    constructor(
        public id?: number,
        public currentDate?: Moment,
        public badgeageEleve?: Moment,
        public badgeageCorrige?: Moment,
        public utilisateur?: IUtilisateur
    ) {}
}

import { Moment } from 'moment';

export interface ICours {
    id?: number;
    nom?: string;
    dateDebut?: Moment;
    dateFin?: Moment;
}

export class Cours implements ICours {
    constructor(public id?: number, public nom?: string, public dateDebut?: Moment, public dateFin?: Moment) {}
}

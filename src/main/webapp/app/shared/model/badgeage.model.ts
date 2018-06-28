import { Moment } from 'moment';

export interface IBadgeage {
    id?: number;
    currentDate?: Moment;
    badgeageEleve?: Moment;
    badgeageCorrige?: Moment;
}

export class Badgeage implements IBadgeage {
    constructor(public id?: number, public currentDate?: Moment, public badgeageEleve?: Moment, public badgeageCorrige?: Moment) {}
}

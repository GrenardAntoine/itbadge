export interface IDescription {
    id?: number;
    contenu?: string;
}

export class Description implements IDescription {
    constructor(public id?: number, public contenu?: string) {}
}

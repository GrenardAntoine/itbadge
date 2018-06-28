export interface IGroupe {
    id?: number;
    nom?: string;
}

export class Groupe implements IGroupe {
    constructor(public id?: number, public nom?: string) {}
}

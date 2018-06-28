export interface IUtilisateur {
    id?: number;
    isAdmin?: boolean;
    isProfesseur?: boolean;
}

export class Utilisateur implements IUtilisateur {
    constructor(public id?: number, public isAdmin?: boolean, public isProfesseur?: boolean) {
        this.isAdmin = false;
        this.isProfesseur = false;
    }
}

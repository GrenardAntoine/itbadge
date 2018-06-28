import { IBadgeage } from 'app/shared/model//badgeage.model';
import { IGroupe } from 'app/shared/model//groupe.model';
import { ICours } from 'app/shared/model//cours.model';
import { IUser } from 'app/core/user/user.model';

export interface IUtilisateur {
    id?: number;
    isAdmin?: boolean;
    isProfesseur?: boolean;
    listBageages?: IBadgeage[];
    groupe?: IGroupe;
    listCours?: ICours[];
    user?: IUser;
}

export class Utilisateur implements IUtilisateur {
    constructor(
        public id?: number,
        public isAdmin?: boolean,
        public isProfesseur?: boolean,
        public listBageages?: IBadgeage[],
        public groupe?: IGroupe,
        public listCours?: ICours[],
        public user?: IUser
    ) {
        this.isAdmin = false;
        this.isProfesseur = false;
    }
}

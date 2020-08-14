import { IUser } from 'app/core/user/user.model';

export interface IPhones {
  id?: number;
  number?: string;
  ddd?: string;
  user?: IUser;
}

export class Phones implements IPhones {
  constructor(public id?: number, public number?: string, public ddd?: string, public user?: IUser) {}
}

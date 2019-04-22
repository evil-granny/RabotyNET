import {Contacts} from "./contacts.model";
import {Address} from "./address.model";

export class Company {
  companyId: BigInteger;

  name: string;

  edrpou: string;

  description: string;

  website: string;

  logo: string;

  approved: boolean;

  contacts: Contacts = new Contacts();

  address: Address = new Address();
}

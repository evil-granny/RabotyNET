import { Address } from './Address.model';
import { Contacts } from './contacts.model';

export class Person {

    userId: bigint;

    firstName: string;

    lastName: string;

    birthday: Date;

    photo: string;

    address: Address = new Address();

    contacts: Contacts = new Contacts();

}
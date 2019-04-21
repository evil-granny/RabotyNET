import { Requirement } from './requirement.model';
import { Observable } from 'rxjs';

export class  Vacancy {

  vacancyId: BigInteger;

  position: string;

  employment: string;

  salary: Int8Array;

  company: BigInteger;

  requirements: Observable<Requirement[]>;

}

import { Skill } from './cv-skill.model';
import { Education } from './cv-education.model';
import { Job } from './cv-job.model';

export class CV {

    cvId: BigInteger;

    photo: string;

    position: string;

    skill: Skill[] = [];

    education: Education = new Education();

    job: Job[] = [];

}

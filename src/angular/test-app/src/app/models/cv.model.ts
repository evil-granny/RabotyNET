import { Skill } from './cv-skill.model';
import { Education } from './cv-education.model';
import { Job } from './cv-job.model';

export class CV {

    cvId: BigInteger;

    photo: string;

    position: string;

    skills: Skill[] = [];

    education: Education = new Education();

    jobs: Job[] = [];

}

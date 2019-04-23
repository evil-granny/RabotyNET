import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CV } from '../models/cv.model';
import { CVService } from './cv.service';
import { Skill } from '../models/cv-skill.model';
import { Job } from '../models/cv-job.model';

@Component({
  templateUrl: './cv-add.component.html'
})
export class AddCvComponent {

  cv: CV = new CV();

  constructor(private router: Router, private cvService: CVService) {}

  insert(): void {
    console.log(this.cv);
    this.cvService.insert(this.cv)
      .subscribe(data => {
        alert("CV has been created successfully.");
      });
  };

  newSkill() {
    console.log(this.cv.skill);
    this.cv.skill.push(new Skill());
  }

  newJob(){
    this.cv.job.push(new Job());
  }

}

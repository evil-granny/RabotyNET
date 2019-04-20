import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Vacancy } from '../../models/vacancy.model';
import { VacancyService } from '../vacancy.service';

@Component({
  templateUrl: './add-vacancy.component.html'
})
export class AddVacancyComponent {

  vacancy: Vacancy = new Vacancy();

  constructor(private router: Router, private personService: VacancyService) {}

  create(): void {
    this.personService.create(this.vacancy)
      .subscribe(data => {
        alert("Vacancy has been created successfully.");
      });
  };

}

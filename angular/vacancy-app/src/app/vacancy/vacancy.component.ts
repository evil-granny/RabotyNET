import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Vacancy } from '../models/vacancy.model';
import { VacancyService } from './vacancy.service';

@Component({
  selector: 'vacancy-app',
  templateUrl: './vacancy.component.html',
  styleUrls: [ './vacancy.component.css' ]
})
export class VacancyComponent implements OnInit {

  vacancies: Vacancy[];

  constructor(private router: Router, private vacancyService: VacancyService) {}

  ngOnInit() {
    this.vacancyService.findAll()
      .subscribe( data => {
        this.vacancies = data;
      });
  };

  deleteById(vacancy: Vacancy): void {
    this.vacancyService.deleteById(vacancy)
      .subscribe( data => {
        this.vacancies = this.vacancies.filter(v => v !== vacancy);
      })
  };

}

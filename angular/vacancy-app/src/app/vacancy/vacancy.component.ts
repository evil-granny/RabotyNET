import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Vacancy } from '../models/vacancy.model';
import { VacancyService } from './vacancy.service';
import { Observable } from 'rxjs';
import { Requirement } from '../models/requirement.model';

@Component({
  selector: 'vacancy-app',
  templateUrl: './vacancy.component.html',
  styleUrls: [ './vacancy.component.css' ]
})
export class VacancyComponent implements OnInit {
  vacancies : Observable<Vacancy[]>;
  requirements : Observable<Requirement[]>;

  constructor(private router: Router, private vacancyService: VacancyService) {}

  ngOnInit() {
    this.reloadData();
  };
  reloadData() {
    this.vacancyService.findAll().subscribe(
      data=>{
        this.vacancies = data;
        console.log(this.vacancies);
      }
    )
  }

  deleteById(id: number): void {
    this.vacancyService.deleteById(id)
    .subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error));
  };

}

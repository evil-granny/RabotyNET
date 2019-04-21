import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

import { Vacancy } from '../../models/vacancy.model';
import { VacancyService } from '../vacancy.service';
import { NgForm } from '@angular/forms';

@Component({
  //selector: 'app-car-edit',
  templateUrl: './vacancy-edit.component.html'
})
export class EditVacancyComponent implements OnInit, OnDestroy{

  vacancy: Vacancy = new Vacancy();

  sub: Subscription;

  constructor(private route: ActivatedRoute,private router: Router, private vacancyService: VacancyService) {}

  create(): void {
    this.vacancyService.create(this.vacancy)
      .subscribe(data => {
        this.gotoList;
      }, error => console.error(error));
  };

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.vacancyService.get(id).subscribe((vacancy: any) => {
          if (vacancy) {
            this.vacancy = vacancy;
          } else {
            console.log(`Vacancy with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/vacancies']);
  }

  save(form: NgForm) {
    this.vacancyService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }


}

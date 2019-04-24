import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

import { Vacancy } from '../../models/vacancy.model';
import { VacancyService } from '../vacancy.service';
import { NgForm, FormControl } from '@angular/forms';
import { Requirement } from 'src/app/models/requirement.model';
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';

@Component({
  templateUrl: './vacancy-edit.component.html'
})
export class EditVacancyComponent implements OnInit{

  vacancy: Vacancy = new Vacancy();
  
  constructor(private route: ActivatedRoute,private router: Router, private vacancyService: VacancyService,
    private fb: FormBuilder) {
    }

   create(): void {
      console.log(this.vacancy);
      this.vacancyService.create(this.vacancy)
        .subscribe(data => {
          this.gotoList();
        }, error => console.error(error));
   };
   ngOnInit(): void {
        let vacancyId = this.route.snapshot.paramMap.get("vacancyId");
        if(vacancyId !== null) {
          this.vacancyService.get(vacancyId)
            .subscribe(data => {
              this.vacancy = data;
            });
        }
      }

      update(vacancy: Vacancy ): void {
             this.vacancyService.save(this.vacancy)
               .subscribe(data => {
                 this.gotoList();
               }, error => console.error(error));
          };
   
  gotoList() {
    this.router.navigate(['/vacancies']);
  }

  Requirement: Array<any>;

  save(form: NgForm):void {
    this.vacancyService.create(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  private fieldArray: Array<any> = [];
  private newAttribute: any = {};

  addFieldValue() {
    console.log(this.vacancy);
    this.vacancy.requirements.push(new Requirement());
  }

  deleteFieldValue(index) {
      this.fieldArray.splice(index, 1);
  }
}

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import { Company } from '../models/company.model';
import { CompanyService } from './company.service';

@Component({
  templateUrl: './add-company.component.html'
})
export class AddCompanyComponent implements OnInit {

  company: Company = new Company();

  constructor(private router: Router, private route: ActivatedRoute, private companyService: CompanyService) {}

  ngOnInit(): void {
    var companyId = this.route.snapshot.paramMap.get("companyId");
    if(companyId !== null) {
      this.companyService.findById(companyId)
        .subscribe(data => {
          this.company = data;
        });
    }
  }

  update(): void {
    this.companyService.update(this.company)
      .subscribe(data => {
        alert("Company has been updated successfully.");
      });
  };

  create(): void {
    this.companyService.create(this.company)
      .subscribe(data => {
        alert("Company has been created successfully.");
      });
  };

}

import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Company } from '../models/company.model';
import { CompanyService } from './company.service';

@Component({
  templateUrl: './add-company.component.html'
})
export class AddCompanyComponent {

  company: Company = new Company();

  constructor(private router: Router, private companyService: CompanyService) {}

  create(): void {
    this.companyService.create(this.company)
      .subscribe(data => {
        alert("Company has been created successfully.");
      });
  };

}

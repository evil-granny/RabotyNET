import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Company } from "../models/company.model";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable()
export class CompanyService {

  constructor(private http: HttpClient) {}

  private companyURL = 'http://localhost:8080';

  public findAll() {
    console.log("[find all companies]");
    return this.http.get<Company[]>(this.companyURL + "/companies");
  }

  public deleteById(company) {
    console.log("[delete company]");
    return this.http.delete(this.companyURL + "/deleteCompany/" + company.companyId);
  }

  public create(company) {
    console.log("[create company]");
    return this.http.post<Company>(this.companyURL + "/createCompany", company);
  }

}

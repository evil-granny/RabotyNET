import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Vacancy } from "../models/vacancy.model";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*' 
  })
};

@Injectable()
export class VacancyService {

  constructor(private http: HttpClient) {}

  private vacancyUrl = 'http://localhost:8080/test';

  public findAll() {
    console.log("[findAll]");
    return this.http.get<Vacancy[]>(this.vacancyUrl + "/vacancies");
  }

  public deleteById(vacancy) {
    console.log("[deleteById]");
    return this.http.delete(this.vacancyUrl + "/vacancy/" + vacancy.vacancyId);
  }

  public create(vacancy) {
    console.log("[create]");
    return this.http.post<Vacancy>(this.vacancyUrl + "/vacancy", vacancy);
  }

}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Vacancy } from "../models/vacancy.model";
import { Observable } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*' 
  })
};

@Injectable()
export class VacancyService {

  constructor(private http: HttpClient) {}

  private vacancyUrl = 'http://localhost:8080';
  private vacancyAPI = this.vacancyUrl+'/vacancy';

  public findAll(): Observable<any> {
    return this.http.get(this.vacancyUrl + "/vacancies");
  }
  
  get(vacancyId : string)   {
    return this.http.get<Vacancy>(this.vacancyAPI + '/' + vacancyId);
  }

  public deleteById(id:number): Observable<Object> {
    return this.http.delete(this.vacancyUrl + "/deleteVacancy/" + id);
  }

  public save(vacancy: any): Observable<any> {
    let result: Observable<Object>;
    if (vacancy['id']) {
      result = this.http.put<Vacancy>(this.vacancyAPI,vacancy.id, vacancy);
    } else {
      result = this.http.post<Vacancy>(this.vacancyUrl+ '/createVacancy'+1, vacancy);
    }
    return result;
  }
  public update(vacancy : Vacancy) {
    return this.http.put<Vacancy>(this.vacancyAPI + vacancy.vacancyId, vacancy);
  }

  
   public create(vacancy : any) : Observable<Object>{
     return this.http.post<Vacancy>(this.vacancyUrl + "/createVacancy/"+1, vacancy);
   }

}

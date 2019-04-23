import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Person } from '../models/person.model';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }

  private personUrl = "http://localhost:8080";

  public findById(): Observable<any> {
    console.log("[findById]");
    return this.http.get<Person>(this.personUrl + "/person/1", httpOptions);
  }

  public update(person: Person): Observable<any> {
    console.log("[update]"); 
    return this.http.put<Person>(this.personUrl + "/update", person, httpOptions);
  }

}

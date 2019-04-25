import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Person } from "../models/person.model";
import {Observable} from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*' 
  })
};

@Injectable()
export class PersonService {

  constructor(private http: HttpClient) {}

  private personUrl = 'http://localhost:8080';

  public findAll(): Observable<any> {
    console.log("[findAll]");
    return this.http.get<Person[]>(this.personUrl + "/people");
  }

  public deleteById(person) {
    console.log("[deleteById]");
    return this.http.delete(this.personUrl + "/delete/" + person.userId);
  }

  public insert(person) {
    console.log("[insert]");
    return this.http.post<Person>(this.personUrl + "/insert", person);
  }

}

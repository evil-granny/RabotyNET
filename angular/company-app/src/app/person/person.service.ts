import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Person } from "../models/person.model";

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

  public findAll() {
    console.log("[findAll]");
    return this.http.get<Person[]>(this.personUrl + "/people");
  }

  public deleteById(person) {
    console.log("[deleteById]");
    return this.http.delete(this.personUrl + "/delete/" + person.userId);
  }

  public create(person) {
    console.log("[create]");
    return this.http.post<Person>(this.personUrl + "/create", person);
  }

}

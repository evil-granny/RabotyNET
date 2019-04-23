import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import {Person} from "../models/person.model";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class SearchCVService {

  constructor(private http: HttpClient) {}

  private searchCVUrl = 'http://localhost:8080';

  public getResult(searchCV){
    console.log("[Start Searching]");
    return this.http.post<Person[]>(this.searchCVUrl + "/searchCV", searchCV, httpOptions);
  }
}

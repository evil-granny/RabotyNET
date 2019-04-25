import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { CV } from "../models/cv.model";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*' 
  })
};

@Injectable()
export class CVService {

  constructor(private http: HttpClient) {}

  private cvUrl = 'http://localhost:8080';
  
  public findAll() {
    console.log("[find all cvs]");
    return this.http.get<CV[]>(this.cvUrl + "/cvs");
  }

  public deleteById(cv) {
    console.log("[delete cv]");
    return this.http.delete(this.cvUrl + "/deleteCV/" + cv.cvId);
  }

  public insert(cv) {
    console.log("[create cv]");
    return this.http.post<CV>(this.cvUrl + "/createCV", cv);
  }

  public update(cv) {
    console.log("[update cv]");
    return this.http.put<CV>(this.cvUrl + "/updateCV/" + cv.cvId, cv);
  }


}

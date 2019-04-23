import { Component } from '@angular/core';
import {Router} from "@angular/router";

import {SearchCVService} from "./search-cv.service";
import {SearchCV} from "../models/searchCV.model";
import {Person} from "../models/person.model";

@Component({
  selector: 'app-search-cv',
  templateUrl: './search-cv.component.html',
  styleUrls: ['./search-cv.component.css']
})

export class SearchCVComponent{

  searchCv: SearchCV = new SearchCV();

  people: Person[];

  constructor(private router: Router, private searchCVService: SearchCVService) {}

  startSearch(){
    this.searchCVService.getResult(this.searchCv)
      .subscribe(data => {
        this.people = data;
      });
  }

}

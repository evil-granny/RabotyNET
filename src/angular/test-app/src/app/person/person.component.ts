import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Person } from '../models/person.model';
import { PersonService } from './person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: [ './person.component.css' ]
})
export class PersonComponent implements OnInit {

  people: Person[];

  constructor(private router: Router, private personService: PersonService) {}

  ngOnInit() {
    this.personService.findAll()
      .subscribe( data => {
        this.people = data;
      });
  };

  deleteById(person: Person): void {
    this.personService.deleteById(person)
      .subscribe( data => {
        this.people = this.people.filter(p => p !== person);
      })
  };

}

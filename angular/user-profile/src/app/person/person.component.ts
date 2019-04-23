import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser'

import { Person } from '../models/person.model';
import { PersonService } from './person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {

  person: Person = new Person();

  fileToUpload: File = null;

  private noImage: string = "/assets/img/no-image.jpg";

  constructor(private router: Router, private personService: PersonService, private sanitizer: DomSanitizer ) { }

  ngOnInit() {
    this.personService.findById()
      .subscribe(data => {
        this.person = data;
      });
  };

  update(): void {
    this.personService.update(this.person)
      .subscribe(() => window.location.reload());
    console.log(this.person);
  };

  handleFileInput(file: FileList) {
    console.log("[handleFileInput]");
    this.fileToUpload = file.item(0);

    var reader = new FileReader();
    reader.onload = (event:any) => {
      this.person.photo = event.target.result;
      console.log(this.person.photo);
    }
    reader.readAsDataURL(this.fileToUpload);
  }

}

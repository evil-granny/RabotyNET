import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CV } from '../models/cv.model';
import { CVService } from './cv.service';


@Component({
  selector: 'app-cv',
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.css']
})
export class CvComponent implements OnInit {

  cvs : CV[];

  constructor(private router: Router, private cvService: CVService) {}

  ngOnInit() {
    this.cvService.findAll()
      .subscribe( data => {
        this.cvs = data;
      });
  };

  deleteById(cv: CV): void {
    this.cvService.deleteById(cv)
      .subscribe( data => {
        this.cvs = this.cvs.filter(p => p !== cv);
      })
  };

}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PersonComponent } from './person/person.component';
import { AddPersonComponent } from './person/add-person.component';
import { CvComponent } from './cv/cv.component';
import { AddCvComponent } from './cv/cv-add.component';


const routes: Routes = [
  { path: 'people', component: PersonComponent },
  { path: 'add', component: AddPersonComponent },
  { path: 'cvs', component: CvComponent },
  { path: 'createCV', component: AddCvComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {}

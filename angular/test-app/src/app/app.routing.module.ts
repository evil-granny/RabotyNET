import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PersonComponent } from './person/person.component';
import { AddPersonComponent } from './person/add-person.component';
import {SearchCVComponent} from "./search-cv/search-cv.component";

const routes: Routes = [
  { path: 'people', component: PersonComponent },
  { path: 'add', component: AddPersonComponent },
  { path: 'searchCV', component:SearchCVComponent }
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

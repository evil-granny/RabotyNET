import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { VacancyComponent } from './vacancy/vacancy.component';
import { AddVacancyComponent } from './vacancy/add/add-vacancy.component';

const routes: Routes = [
  { path: 'vacancies', component: VacancyComponent },
  { path: 'add', component: AddVacancyComponent }
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

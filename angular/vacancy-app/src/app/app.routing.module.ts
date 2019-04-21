import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { VacancyComponent } from './vacancy/vacancy.component';
import { EditVacancyComponent } from './vacancy/edit/vacancy-edit.component';

const routes: Routes = [
  { 
    path: '', redirectTo: '/vacancies', pathMatch: 'full' 
  },
  { 
    path: 'vacancies', component: VacancyComponent 
  },
  { 
    path: 'vacancy', component: EditVacancyComponent 
  },
  {
    path: 'vacancy/:id',
    component: EditVacancyComponent
  }
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

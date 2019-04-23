import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { VacancyComponent } from './vacancy/vacancy.component';
import { AppRoutingModule } from './app.routing.module';
import { VacancyService } from './vacancy/vacancy.service';
import { HttpClientModule } from '@angular/common/http';
import { EditVacancyComponent } from './vacancy/edit/vacancy-edit.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    VacancyComponent,
    EditVacancyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [VacancyService],
  bootstrap: [AppComponent]
})
export class AppModule {}

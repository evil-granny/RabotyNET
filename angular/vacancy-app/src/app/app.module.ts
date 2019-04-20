import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { VacancyComponent } from './vacancy/vacancy.component';
import { AppRoutingModule } from './app.routing.module';
import { VacancyService } from './vacancy/vacancy.service';
import { HttpClientModule } from '@angular/common/http';
import { AddVacancyComponent } from './vacancy/add/add-vacancy.component';

@NgModule({
  declarations: [
    AppComponent,
    VacancyComponent,
    AddVacancyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [VacancyService],
  bootstrap: [AppComponent]
})
export class AppModule {}

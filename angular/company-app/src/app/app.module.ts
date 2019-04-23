import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { PersonComponent } from './person/person.component';
import { AddPersonComponent } from './person/add-person.component';
import { CompanyComponent } from './company/company.component';
import { AddCompanyComponent} from "./company/add-company.component";

import { PersonService } from './person/person.service';
import { CompanyService } from './company/company.service';

import { AppRoutingModule } from './app.routing.module';
import { HttpClientModule } from '@angular/common/http';

import { AddPersonComponent } from './person/add-person.component';
import { SearchCVComponent } from './search-cv/search-cv.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    CompanyComponent,
    AddPersonComponent,
    AddCompanyComponent
    AddPersonComponent,
    SearchCVComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [PersonService, CompanyService],
  bootstrap: [AppComponent]
})
export class AppModule {}

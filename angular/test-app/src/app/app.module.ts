import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { PersonComponent } from './person/person.component';
import { AppRoutingModule } from './app.routing.module';
import { PersonService } from './person/person.service';
import { HttpClientModule } from '@angular/common/http';
import { AddPersonComponent } from './person/add-person.component';
import { SearchCVComponent } from './search-cv/search-cv.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    AddPersonComponent,
    SearchCVComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [PersonService],
  bootstrap: [AppComponent]
})
export class AppModule {}

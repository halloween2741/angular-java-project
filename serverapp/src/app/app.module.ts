import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AgGridModule } from 'ag-grid-angular';
import { HttpClientModule } from '@angular/common/http';
import { SelectComponent } from './components/select/select.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectModule } from '@angular/material/select';
import { InputNumberComponent } from './components/input-number/input-number.component';
import { MatButtonModule } from '@angular/material/button';
import { ButtonComponent } from './components/button/button.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    AppComponent,
    ButtonComponent,
    SelectComponent,
    InputNumberComponent,
    ButtonComponent
  ],
  imports: [
    BrowserModule,
    AgGridModule.withComponents([]),
    HttpClientModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

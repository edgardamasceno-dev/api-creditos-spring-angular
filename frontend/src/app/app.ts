import { importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ConsultaCreditosComponent } from './features/consulta-creditos';
import { registerLocaleData } from '@angular/common';
import ptBr from '@angular/common/locales/pt';
import { LOCALE_ID } from '@angular/core';

registerLocaleData(ptBr, 'pt-BR');

bootstrapApplication(ConsultaCreditosComponent, {
  providers: [
    provideRouter([]),
    importProvidersFrom(HttpClientModule),
    { provide: LOCALE_ID, useValue: 'pt-BR' }
  ],
});

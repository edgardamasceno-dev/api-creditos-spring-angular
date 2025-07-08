import { importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { ConsultaCreditosComponent } from './features/consulta-creditos';

bootstrapApplication(ConsultaCreditosComponent, {
  providers: [provideRouter([]), importProvidersFrom()],
});

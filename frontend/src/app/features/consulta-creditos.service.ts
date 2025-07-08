import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Credito {
  numeroCredito: string;
  numeroNfse: string;
  dataConstituicao: string;
  valorIssqn: number;
  tipoCredito: string;
  simplesNacional: boolean | string;
  aliquota: number;
  valorFaturado: number;
  valorDeducao: number;
  baseCalculo: number;
}

function getApiUrl(): string {
  if (typeof window !== 'undefined') {
    const env = (window as unknown as Record<string, unknown>)['__env'] as
      | { API_URL?: string }
      | undefined;
    if (env && typeof env.API_URL === 'string') {
      return env.API_URL;
    }
  }
  return '/api/creditos';
}

@Injectable({ providedIn: 'root' })
export class ConsultaCreditosService {
  private apiUrl = getApiUrl();
  private http = inject(HttpClient);

  buscarPorNfse(numeroNfse: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${this.apiUrl}/${numeroNfse}`);
  }

  buscarPorCredito(numeroCredito: string): Observable<Credito> {
    return this.http.get<Credito>(`${this.apiUrl}/credito/${numeroCredito}`);
  }
}

import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ConsultaCreditosService, Credito } from './consulta-creditos.service';

@Component({
  selector: 'app-consulta-creditos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './consulta-creditos.html',
  styleUrls: ['./consulta-creditos.scss']
})
export class ConsultaCreditosComponent {
  form: FormGroup;
  creditos: Credito[] = [];
  loading = false;
  error = '';
  modoBusca: 'nfse' | 'credito' = 'nfse';

  private fb = inject(FormBuilder);
  private service = inject(ConsultaCreditosService);

  constructor() {
    this.form = this.fb.group({
      numeroNfse: ['', []],
      numeroCredito: ['', []]
    });
    this.setModoBusca('nfse');
  }

  setModoBusca(modo: 'nfse' | 'credito') {
    this.modoBusca = modo;
    if (modo === 'nfse') {
      this.form.get('numeroNfse')?.setValidators([Validators.required]);
      this.form.get('numeroCredito')?.setValidators([]);
      this.form.get('numeroCredito')?.setValue('');
    } else {
      this.form.get('numeroCredito')?.setValidators([Validators.required]);
      this.form.get('numeroNfse')?.setValidators([]);
      this.form.get('numeroNfse')?.setValue('');
    }
    this.form.get('numeroNfse')?.updateValueAndValidity();
    this.form.get('numeroCredito')?.updateValueAndValidity();
  }

  onSubmit() {
    this.error = '';
    this.creditos = [];
    if (this.form.invalid) {
      return;
    }
    const { numeroNfse, numeroCredito } = this.form.value;
    this.loading = true;
    if (this.modoBusca === 'nfse' && numeroNfse) {
      this.service.buscarPorNfse(numeroNfse).subscribe({
        next: (res: Credito[]) => {
          this.creditos = res || [];
          this.loading = false;
        },
        error: () => {
          this.error = 'Erro ao buscar créditos por NFS-e.';
          this.loading = false;
        }
      });
    } else if (this.modoBusca === 'credito' && numeroCredito) {
      this.service.buscarPorCredito(numeroCredito).subscribe({
        next: (res: Credito) => {
          this.creditos = res ? [res] : [];
          this.loading = false;
        },
        error: () => {
          this.error = 'Erro ao buscar crédito por número.';
          this.loading = false;
        }
      });
    }
  }
}

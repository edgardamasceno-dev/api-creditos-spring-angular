import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ConsultaCreditosService, Credito } from './consulta-creditos.service';

@Component({
  selector: 'app-consulta-creditos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './consulta-creditos.html',
  styleUrls: ['./consulta-creditos.scss'],
})
export class ConsultaCreditosComponent {
  form: FormGroup;
  creditos: Credito[] = [];
  loading = false;
  error = '';

  private fb = inject(FormBuilder);
  private service = inject(ConsultaCreditosService);

  constructor() {
    this.form = this.fb.group(
      {
        numeroNfse: [''],
        numeroCredito: [''],
      },
      { validators: [this.peloMenosUmCampo()] },
    );
  }

  peloMenosUmCampo() {
    return (group: FormGroup) => {
      const nfse = group.get('numeroNfse')?.value;
      const credito = group.get('numeroCredito')?.value;
      return nfse || credito ? null : { required: true };
    };
  }

  onSubmit() {
    this.error = '';
    this.creditos = [];
    if (this.form.invalid) return;
    const { numeroNfse, numeroCredito } = this.form.value;
    this.loading = true;
    if (numeroNfse) {
      this.service.buscarPorNfse(numeroNfse).subscribe({
        next: (res: Credito[]) => {
          this.creditos = res || [];
          this.loading = false;
        },
        error: () => {
          this.error = 'Erro ao buscar créditos por NFS-e.';
          this.loading = false;
        },
      });
    } else if (numeroCredito) {
      this.service.buscarPorCredito(numeroCredito).subscribe({
        next: (res: Credito) => {
          this.creditos = res ? [res] : [];
          this.loading = false;
        },
        error: () => {
          this.error = 'Erro ao buscar crédito por número.';
          this.loading = false;
        },
      });
    }
  }
}

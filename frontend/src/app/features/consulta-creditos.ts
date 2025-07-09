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
  creditoSelecionado: Credito | null = null;
  isMobile = false;

  maxLengthNfse = 50;
  maxLengthCredito = 50;

  readonly MAX_LENGTH = 50;

  private fb = inject(FormBuilder);
  private service = inject(ConsultaCreditosService);

  constructor() {
    this.form = this.fb.group({
      numeroNfse: ['', [Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]],
      numeroCredito: ['', [Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]]
    });
    this.setModoBusca('nfse');
  }

  ngOnInit() {
    this.checkMobile();
    window.addEventListener('resize', this.checkMobile.bind(this));
  }

  ngOnDestroy() {
    window.removeEventListener('resize', this.checkMobile.bind(this));
  }

  checkMobile() {
    this.isMobile = window.innerWidth <= 700;
  }

  setModoBusca(modo: 'nfse' | 'credito') {
    this.modoBusca = modo;
    if (modo === 'nfse') {
      this.form.get('numeroNfse')?.setValidators([Validators.required, Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]);
      this.form.get('numeroCredito')?.setValidators([Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]);
      this.form.get('numeroCredito')?.setValue('');
    } else {
      this.form.get('numeroCredito')?.setValidators([Validators.required, Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]);
      this.form.get('numeroNfse')?.setValidators([Validators.maxLength(this.MAX_LENGTH), Validators.pattern('^[0-9]*$')]);
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

  abrirDetalhes(credito: Credito) {
    this.creditoSelecionado = credito;
  }

  fecharModal() {
    this.creditoSelecionado = null;
  }

  permitirApenasNumeros(event: KeyboardEvent) {
    const allowedKeys = [
      'Backspace', 'Delete', 'Tab', 'ArrowLeft', 'ArrowRight', 'Home', 'End'
    ];
    // Permite Ctrl/Cmd + V, Ctrl/Cmd + C, Ctrl/Cmd + X, Ctrl/Cmd + A
    if ((event.ctrlKey || event.metaKey) && ['a', 'c', 'v', 'x'].includes(event.key.toLowerCase())) {
      return;
    }
    if (
      allowedKeys.includes(event.key) ||
      (event.key.length === 1 && /[0-9]/.test(event.key))
    ) {
      return;
    }
    event.preventDefault();
  }

  limparNaoNumericos(controlName: 'numeroNfse' | 'numeroCredito') {
    const valor = this.form.get(controlName)?.value;
    if (valor && typeof valor === 'string') {
      const limpo = valor.replace(/[^0-9]/g, '');
      if (valor !== limpo) {
        this.form.get(controlName)?.setValue(limpo, { emitEvent: false });
      }
    }
  }
}

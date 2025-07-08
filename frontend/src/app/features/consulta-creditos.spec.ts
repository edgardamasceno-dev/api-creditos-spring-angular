import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { ConsultaCreditosComponent } from './consulta-creditos';
import { ConsultaCreditosService } from './consulta-creditos.service';

describe('ConsultaCreditosComponent', () => {
  let component: ConsultaCreditosComponent;
  let fixture: ComponentFixture<ConsultaCreditosComponent>;
  let httpTestingController: HttpTestingController;
  let service: ConsultaCreditosService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaCreditosComponent, HttpClientTestingModule, ReactiveFormsModule],
      providers: [ConsultaCreditosService]
    }).compileComponents();

    fixture = TestBed.createComponent(ConsultaCreditosComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(ConsultaCreditosService);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should search by NFS-e number', () => {
    const mockCreditos = [
      {
        numeroCredito: '123456',
        numeroNfse: '7891011',
        dataConstituicao: '2024-02-25',
        valorIssqn: 1500.75,
        tipoCredito: 'ISSQN',
        simplesNacional: 'Sim',
        aliquota: 5.0,
        valorFaturado: 30000.00,
        valorDeducao: 5000.00,
        baseCalculo: 25000.00
      }
    ];

    component.setModoBusca('nfse');
    component.form.patchValue({ numeroNfse: '7891011' });
    component.onSubmit();

    const req = httpTestingController.expectOne('/api/creditos/7891011');
    expect(req.request.method).toBe('GET');
    req.flush(mockCreditos);

    expect(component.creditos).toEqual(mockCreditos);
  });

  it('should search by credit number', () => {
    const mockCredito = {
      numeroCredito: '123456',
      numeroNfse: '7891011',
      dataConstituicao: '2024-02-25',
      valorIssqn: 1500.75,
      tipoCredito: 'ISSQN',
      simplesNacional: 'Sim',
      aliquota: 5.0,
      valorFaturado: 30000.00,
      valorDeducao: 5000.00,
      baseCalculo: 25000.00
    };

    component.setModoBusca('credito');
    component.form.patchValue({ numeroCredito: '123456' });
    component.onSubmit();

    const req = httpTestingController.expectOne('/api/creditos/credito/123456');
    expect(req.request.method).toBe('GET');
    req.flush(mockCredito);

    expect(component.creditos).toEqual([mockCredito]);
  });

  it('should handle error when searching by NFS-e', () => {
    component.setModoBusca('nfse');
    component.form.patchValue({ numeroNfse: '999999' });
    component.onSubmit();

    const req = httpTestingController.expectOne('/api/creditos/999999');
    req.flush('Not found', { status: 404, statusText: 'Not Found' });

    expect(component.error).toBe('Erro ao buscar créditos por NFS-e.');
  });

  it('should handle error when searching by credit number', () => {
    component.setModoBusca('credito');
    component.form.patchValue({ numeroCredito: '999999' });
    component.onSubmit();

    const req = httpTestingController.expectOne('/api/creditos/credito/999999');
    req.flush('Not found', { status: 404, statusText: 'Not Found' });

    expect(component.error).toBe('Erro ao buscar crédito por número.');
  });
});

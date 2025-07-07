package com.infuse.creditos.service;

import com.infuse.creditos.domain.Credito;
import com.infuse.creditos.repository.CreditoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {
    private final CreditoRepository repository;

    public CreditoService(CreditoRepository repository) {
        this.repository = repository;
    }

    public Optional<Credito> buscarPorNumeroCredito(String numeroCredito) {
        return repository.findByNumeroCredito(numeroCredito);
    }

    public List<Credito> buscarPorNumeroNfse(String numeroNfse) {
        return repository.findAllByNumeroNfse(numeroNfse);
    }
}

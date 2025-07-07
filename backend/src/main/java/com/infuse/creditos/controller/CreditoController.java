package com.infuse.creditos.controller;

import com.infuse.creditos.domain.Credito;
import com.infuse.creditos.dto.CreditoDTO;
import com.infuse.creditos.dto.CreditoMapper;
import com.infuse.creditos.service.CreditoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {
    private final CreditoService service;

    public CreditoController(CreditoService service) {
        this.service = service;
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> getByNumeroNfse(@PathVariable String numeroNfse) {
        List<Credito> lista = service.buscarPorNumeroNfse(numeroNfse);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(CreditoMapper.toDTOList(lista));
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> getByNumeroCredito(@PathVariable String numeroCredito) {
        Optional<Credito> credito = service.buscarPorNumeroCredito(numeroCredito);
        if (credito.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(CreditoMapper.toDTO(credito.get()));
    }
}

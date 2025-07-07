package com.infuse.creditos.controller;

import com.infuse.creditos.domain.Credito;
import com.infuse.creditos.dto.CreditoDTO;
import com.infuse.creditos.dto.CreditoMapper;
import com.infuse.creditos.service.CreditoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Tag(name = "Créditos", description = "Operações de consulta de créditos constituídos")
@RestController
@RequestMapping("/api/creditos")
public class CreditoController {
    private final CreditoService service;

    public CreditoController(CreditoService service) {
        this.service = service;
    }

    @Operation(summary = "Buscar créditos por número de NFS-e", description = "Retorna uma lista de créditos associados ao número de NFS-e informado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de créditos encontrada"),
        @ApiResponse(responseCode = "404", description = "Nenhum crédito encontrado para o número de NFS-e informado")
    })
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> getByNumeroNfse(
            @Parameter(description = "Número da NFS-e", example = "7891011")
            @PathVariable String numeroNfse) {
        List<Credito> lista = service.buscarPorNumeroNfse(numeroNfse);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(CreditoMapper.toDTOList(lista));
    }

    @Operation(summary = "Buscar crédito por número de crédito", description = "Retorna o crédito associado ao número de crédito informado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Crédito encontrado"),
        @ApiResponse(responseCode = "404", description = "Crédito não encontrado para o número informado")
    })
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> getByNumeroCredito(
            @Parameter(description = "Número do crédito", example = "123456")
            @PathVariable String numeroCredito) {
        Optional<Credito> credito = service.buscarPorNumeroCredito(numeroCredito);
        if (credito.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(CreditoMapper.toDTO(credito.get()));
    }
}

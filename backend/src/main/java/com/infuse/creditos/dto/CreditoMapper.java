package com.infuse.creditos.dto;

import com.infuse.creditos.domain.Credito;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreditoMapper {
    public static CreditoDTO toDTO(Credito credito) {
        if (credito == null) return null;
        CreditoDTO dto = new CreditoDTO();
        dto.setNumeroCredito(credito.getNumeroCredito());
        dto.setNumeroNfse(credito.getNumeroNfse());
        dto.setDataConstituicao(credito.getDataConstituicao());
        dto.setValorIssqn(credito.getValorIssqn());
        dto.setTipoCredito(credito.getTipoCredito());
        dto.setSimplesNacional(credito.isSimplesNacional() ? "Sim" : "Não");
        dto.setAliquota(credito.getAliquota());
        dto.setValorFaturado(credito.getValorFaturado());
        dto.setValorDeducao(credito.getValorDeducao());
        dto.setBaseCalculo(credito.getBaseCalculo());
        return dto;
    }

    public static List<CreditoDTO> toDTOList(List<Credito> lista) {
        if (lista == null) return java.util.Collections.emptyList();
        return lista.stream().filter(Objects::nonNull).map(CreditoMapper::toDTO).collect(Collectors.toList());
    }
}

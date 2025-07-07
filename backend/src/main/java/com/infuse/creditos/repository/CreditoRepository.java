package com.infuse.creditos.repository;

import com.infuse.creditos.domain.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    Optional<Credito> findByNumeroCredito(String numeroCredito);
    List<Credito> findAllByNumeroNfse(String numeroNfse);
}

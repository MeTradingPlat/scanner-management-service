package com.metradingplat.scanner_management.infrastructure.output.persistence.repositories;

import com.metradingplat.scanner_management.domain.enums.EnumMercado;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.MercadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MercadoRepositoryInt extends JpaRepository<MercadoEntity, Long> {

    Optional<MercadoEntity> findByEnumMercado(EnumMercado enumMercado);

    List<MercadoEntity> findByEnumMercadoIn(List<EnumMercado> enumMercados);
}
package com.metradingplat.scanner_management.infrastructure.output.persistence.repositories;

import com.metradingplat.scanner_management.domain.enums.EnumTipoEjecucion;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.TipoEjecucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEjecucionRepositoryInt extends JpaRepository<TipoEjecucionEntity, Long> {
    Optional<TipoEjecucionEntity> findByEnumTipoEjecucion(EnumTipoEjecucion enumTipoEjecucion);
}
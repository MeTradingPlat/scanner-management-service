package com.metradingplat.scanner_management.infrastructure.output.persistence.repositories;

import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.EstadoEscanerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoEscanerRepositoryInt extends JpaRepository<EstadoEscanerEntity, Long> {

}
package com.metradingplat.scanner_management.infrastructure.output.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.EscanerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EscanerRepositoryInt extends JpaRepository<EscanerEntity, Long> {

       boolean existsByNombre(String nombre);

       boolean existsByIdEscanerNotAndNombre(Long idEscaner, String nombre);

       @Query("""
                     SELECT DISTINCT e FROM EscanerEntity e
                     LEFT JOIN FETCH e.objEstado s
                     LEFT JOIN FETCH e.objTipoEjecucion te
                     LEFT JOIN FETCH e.mercados m
                     WHERE e.idEscaner = :idEscaner
                     """)
       Optional<EscanerEntity> findByIdWithFiltros(@Param("idEscaner") Long idEscaner);

       // Desarchivados: nombre + estado cargado
       @Query("""
                     SELECT DISTINCT e FROM EscanerEntity e
                     JOIN FETCH e.objEstado s
                     WHERE s.enumEstadoEscaner <> 'ARCHIVADO'
                     """)
       List<EscanerEntity> findDesarchivadosWithEstado();

       // Archivados: filtra por estado ARCHIVADO, no hace fetch adicional
       @Query("""
                     SELECT e FROM EscanerEntity e
                     JOIN e.objEstado s
                     WHERE s.enumEstadoEscaner = 'ARCHIVADO'
                     """)
       List<EscanerEntity> findArchivadosBasic();
}
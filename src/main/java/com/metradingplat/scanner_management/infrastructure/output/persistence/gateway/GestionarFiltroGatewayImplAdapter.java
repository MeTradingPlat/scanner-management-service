package com.metradingplat.scanner_management.infrastructure.output.persistence.gateway;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.EscanerEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.FiltroEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.mappers.FiltroMapperPersistencia;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.EscanerRepositoryInt;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.FiltroRepositoryInt;

@Service
@RequiredArgsConstructor
public class GestionarFiltroGatewayImplAdapter implements GestionarFiltroGatewayIntPort {

    private final FiltroRepositoryInt objFiltroRepository;
    private final EscanerRepositoryInt objEscanerRepository;
    private final FiltroMapperPersistencia objMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Filtro> obtenerFiltrosGuardados(Long idEscaner) {
        List<FiltroEntity> entities = this.objFiltroRepository.findFiltrosByEscanerId(idEscaner);
        List<Filtro> filtros = this.objMapper.mappearListaEntityAFiltro(entities);
        return filtros;
    }

    @Override
    @Transactional
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros) {
        EscanerEntity escanerEntity = this.objEscanerRepository.findById(idEscaner).get();
        escanerEntity.getFiltros().clear();
        List<FiltroEntity> filtroEntities = this.objMapper.mappearListaFiltroAEntity(filtros);
        escanerEntity.getFiltros().addAll(filtroEntities);
        escanerEntity.asociarTodo();
        EscanerEntity savedEscaner = this.objEscanerRepository.save(escanerEntity);
        return this.objMapper.mappearListaEntityAFiltro(savedEscaner.getFiltros());
    }
}

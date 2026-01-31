package com.metradingplat.scanner_management.infrastructure.output.persistence.gateway;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.EscanerEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.MercadoEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.TipoEjecucionEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.mappers.EscanerMapperPersistencia;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.EscanerRepositoryInt;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.MercadoRepositoryInt;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.TipoEjecucionRepositoryInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GestionarEscanerGatewayImplAdapter implements GestionarEscanerGatewayIntPort {

    private final EscanerRepositoryInt objEscanerRepository;
    private final EscanerMapperPersistencia objMapper;
    private final TipoEjecucionRepositoryInt objTipoEjecucionRepository;
    private final MercadoRepositoryInt objMercadoRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean existeEscanerPorNombre(String nombre) {
        return this.objEscanerRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existeEscanerPorNombre(Long idEscaner, String nombre) {
        return this.objEscanerRepository.existsByIdEscanerNotAndNombre(idEscaner, nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existeEscanerPorId(Long idEscaner) {
        return this.objEscanerRepository.existsById(idEscaner);
    }

    @Override
    @Transactional
    public Escaner crearEscaner(Escaner escaner) {
        EscanerEntity entity = this.objMapper.mappearDeEscanerAEntity(escaner);
        TipoEjecucionEntity objTipoEjecucion = this.objTipoEjecucionRepository
                .findByEnumTipoEjecucion(entity.getObjTipoEjecucion().getEnumTipoEjecucion()).get();
        List<EnumMercado> enumMercados = entity.getMercados().stream()
                .map(MercadoEntity::getEnumMercado)
                .collect(Collectors.toList());
        List<MercadoEntity> mercados = this.objMercadoRepository.findByEnumMercadoIn(enumMercados);
        entity.setMercados(mercados);
        entity.setObjTipoEjecucion(objTipoEjecucion);
        entity.asociarTodo();
        EscanerEntity saved = this.objEscanerRepository.save(entity);
        return this.objMapper.mappearDeEntityAEscaner(saved);
    }

    @Override
    @Transactional
    public Escaner actualizarEscaner(Escaner escaner) {
        EscanerEntity entity = this.objEscanerRepository.findById(escaner.getIdEscaner()).get();
        entity.setNombre(escaner.getNombre());
        entity.setDescripcion(escaner.getDescripcion());
        entity.setHoraInicio(escaner.getHoraInicio());
        entity.setHoraFin(escaner.getHoraFin());
        TipoEjecucionEntity objTipoEjecucion = this.objTipoEjecucionRepository
                .findByEnumTipoEjecucion(escaner.getObjTipoEjecucion().getEnumTipoEjecucion())
                .get();
        List<EnumMercado> enumMercados = escaner.getMercados().stream()
                .map(m -> m.getEnumMercado())
                .collect(Collectors.toList());
        List<MercadoEntity> mercados = this.objMercadoRepository.findByEnumMercadoIn(enumMercados);
        entity.setObjTipoEjecucion(objTipoEjecucion);
        entity.setMercados(mercados);
        entity.asociarTodo();
        EscanerEntity saved = this.objEscanerRepository.save(entity);
        return this.objMapper.mappearDeEntityAEscaner(saved);
    }

    @Override
    @Transactional
    public Boolean eliminarEscaner(Long idEscaner) {
        this.objEscanerRepository.deleteById(idEscaner);
        Boolean respuesta = !this.objEscanerRepository.existsById(idEscaner);
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public Escaner obtenerEscanerPorId(Long idEscaner) {
        EscanerEntity entity = this.objEscanerRepository.findByIdWithFiltros(idEscaner).get();
        Escaner escaner = this.objMapper.mappearDeEntityAEscaner(entity);
        return escaner;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Escaner> obtenerEscaneresDesarchivados() {
        List<EscanerEntity> entitys = this.objEscanerRepository.findDesarchivadosWithEstado();
        List<Escaner> escaneres = this.objMapper.mappearListaDeEntityAEscaner(entitys);
        return escaneres;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Escaner> obtenerEscaneresArchivados() {
        List<EscanerEntity> entitys = this.objEscanerRepository.findArchivadosBasic();
        List<Escaner> escaneres = this.objMapper.mappearListaDeEntityAEscaner(entitys);
        return escaneres;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Escaner> obtenerEscaneresIniciados() {
        List<EscanerEntity> entitys = this.objEscanerRepository.findIniciadosWithEstado();
        List<Escaner> escaneres = this.objMapper.mappearListaDeEntityAEscaner(entitys);
        return escaneres;
    }
}

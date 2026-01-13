package com.metradingplat.scanner_management.infrastructure.output.persistence.gateway;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoEscaner;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.EscanerEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entitys.EstadoEscanerEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.mappers.EscanerMapperPersistencia;
import com.metradingplat.scanner_management.infrastructure.output.persistence.repositories.EscanerRepositoryInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GestionarEstadoEscanerGatewayImplAdapter implements GestionarEstadoEscanerGatewayIntPort {

    private final EscanerRepositoryInt objEscanerRepository;
    private final EscanerMapperPersistencia objMapper;

    @Override
    @Transactional(readOnly = true)
    public EnumEstadoEscaner obtenerEstadoDeEscanerActual(Long id) {
        EscanerEntity entity = this.objEscanerRepository.findById(id).get();
        EnumEstadoEscaner estadoActual = entity.getObjEstado().getEnumEstadoEscaner();
        return estadoActual;
    }

    @Override
    @Transactional
    public EstadoEscaner cambiarEstadoDeEscaner(Escaner escaner, EnumEstadoEscaner nuevoEstadoEnum) {

        EscanerEntity escanerEntity = this.objEscanerRepository.findById(escaner.getIdEscaner()).get();

        EstadoEscanerEntity estadoEscanerEntity = escanerEntity.getObjEstado();
        if (estadoEscanerEntity == null) {
            estadoEscanerEntity = new EstadoEscanerEntity();
            estadoEscanerEntity.setIdEscaner(escaner.getIdEscaner());
            estadoEscanerEntity.setObjEscaner(escanerEntity);
            escanerEntity.setObjEstado(estadoEscanerEntity);
        }
        estadoEscanerEntity.setEnumEstadoEscaner(nuevoEstadoEnum);

        EscanerEntity updatedEntity = this.objEscanerRepository.save(escanerEntity);
        return this.objMapper.mappearDeEntityAEscaner(updatedEntity).getObjEstado();
    }
}
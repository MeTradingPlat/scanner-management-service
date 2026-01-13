package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Mercado;
import com.metradingplat.scanner_management.domain.models.TipoEjecucion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EscanerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EstadoEscanerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.TipoEjecucionDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition.EscanerDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition.MercadoDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition.TipoEjecucionDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.DTOAnswer.MercadoDTORespuesta;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EscanerMapperInfraestructuraDominio {

    // --- Escaner ---
    Escaner mappearDePeticionAEscaner(EscanerDTOPeticion peticion);

    EscanerDTORespuesta mappearDeEscanerARespuesta(Escaner escaner);

    List<EscanerDTORespuesta> mappearListaDeEscanerARespuesta(List<Escaner> escaneres);

    // --- EstadoEscaner ---
    EstadoEscanerDTORespuesta mappearDeEstadoEscanerARespuesta(EstadoEscaner estado);

    // --- TipoEjecucion ---
    TipoEjecucion mappearDePeticionATipoEjecucion(TipoEjecucionDTOPeticion peticion);

    TipoEjecucionDTORespuesta mappearDeTipoEjecucionARespuesta(TipoEjecucion tipo);

    List<TipoEjecucionDTORespuesta> mappearListaDeTipoEjecucionARespuesta(List<TipoEjecucion> tipos);

    // --- Mercado ---
    Mercado mappearDePeticionAMercado(MercadoDTOPeticion peticion);

    MercadoDTORespuesta mappearDeMercadoARespuesta(Mercado mercado);

    List<MercadoDTORespuesta> mappearListaDeMercadoARespuesta(List<Mercado> mercados);
}

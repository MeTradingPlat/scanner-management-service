package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.domain.models.ParametroIndicadorSalida;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.mappers.FiltroMapperInfraestructuraDominio;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.DTOAnswer.IndicadorSalidaDtoRespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.DTOAnswer.ParametroIndicadorSalidaDTORespuesta;

/**
 * Solo direccion de respuesta (lectura de catalogo) -- todavia no existe
 * endpoint para guardar indicadores de salida seleccionados por un escaner,
 * asi que no hace falta mapeo de peticion.
 */
@Mapper(componentModel = "spring", uses = FiltroMapperInfraestructuraDominio.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndicadorSalidaMapperInfraestructuraDominio {

    IndicadorSalidaDtoRespuesta mappearDeIndicadorSalidaARespuesta(IndicadorSalida indicadorSalida);

    List<IndicadorSalidaDtoRespuesta> mappearListaDeIndicadorSalidaARespuesta(List<IndicadorSalida> indicadores);

    ParametroIndicadorSalidaDTORespuesta mappearDeParametroIndicadorSalidaARespuesta(
            ParametroIndicadorSalida parametro);

    List<ParametroIndicadorSalidaDTORespuesta> mappearListaDeParametroIndicadorSalidaARespuesta(
            List<ParametroIndicadorSalida> parametros);
}

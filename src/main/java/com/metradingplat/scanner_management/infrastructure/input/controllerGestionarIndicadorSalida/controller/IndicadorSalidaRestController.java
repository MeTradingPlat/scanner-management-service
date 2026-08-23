package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import com.metradingplat.scanner_management.application.input.GestionarIndicadorSalidaCUIntPort;
import com.metradingplat.scanner_management.application.output.FuenteMensajesIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.DTOAnswer.IndicadorSalidaDtoRespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.mappers.IndicadorSalidaMapperInfraestructuraDominio;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Catalogo de indicadores de salida (stop loss / take profit). Separado a
 * proposito de FiltroRestController (/escaner/filtro): estos indicadores no
 * participan en el escaneo de entrada, definen niveles de salida para
 * posiciones ya abiertas. Solo lectura de catalogo por ahora -- todavia no
 * hay endpoint para asociar un indicador de salida a un escaner.
 */
@RestController
@RequestMapping("/escaner/indicador-salida")
@RequiredArgsConstructor
@Validated
public class IndicadorSalidaRestController {

    private final GestionarIndicadorSalidaCUIntPort objGestionarIndicadorSalidaCUInt;
    private final IndicadorSalidaMapperInfraestructuraDominio objMapper;
    private final FuenteMensajesIntPort objFuenteMensajes;

    @GetMapping
    public ResponseEntity<List<IndicadorSalidaDtoRespuesta>> obtenerIndicadoresSalida() {
        List<IndicadorSalida> indicadores = this.objGestionarIndicadorSalidaCUInt.obtenerIndicadoresSalida();
        List<IndicadorSalidaDtoRespuesta> indicadoresDTO = this.objMapper
                .mappearListaDeIndicadorSalidaARespuesta(indicadores);
        this.objFuenteMensajes.internacionalizarIndicadoresSalida(indicadoresDTO);
        return ResponseEntity.ok(indicadoresDTO);
    }

    @GetMapping("/defecto")
    public ResponseEntity<IndicadorSalidaDtoRespuesta> obtenerIndicadorSalidaPorDefecto(
            @RequestParam("indicador") @NotNull(message = "validation.enum.invalid") EnumIndicadorSalida enumIndicadorSalida) {
        IndicadorSalida indicador = this.objGestionarIndicadorSalidaCUInt
                .obtenerIndicadorSalidaPorDefecto(enumIndicadorSalida);
        IndicadorSalidaDtoRespuesta indicadorDTO = this.objMapper.mappearDeIndicadorSalidaARespuesta(indicador);
        this.objFuenteMensajes.internacionalizarIndicadorSalida(indicadorDTO);
        return ResponseEntity.ok(indicadorDTO);
    }
}

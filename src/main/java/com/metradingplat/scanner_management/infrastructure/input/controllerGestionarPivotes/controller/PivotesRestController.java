package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarPivotes.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metradingplat.scanner_management.application.input.GestionarPivotesCUIntPort;
import com.metradingplat.scanner_management.domain.models.PivotesEncontrados;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarPivotes.DTOAnswer.PivotLevelDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarPivotes.DTOAnswer.PivotesDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna.PivotesNoDisponiblesException;

import lombok.RequiredArgsConstructor;

/**
 * Proxy hacia signal-processing-service (no expuesto en el gateway, ver
 * application.yml del gateway) -- mismo patron que CalendarioRestController.
 * Endpoint de exploracion para el chart de Activos: no persiste nada ni se
 * asocia a ningun escaner todavia.
 */
@RestController
@RequestMapping("/escaner/pivots")
@RequiredArgsConstructor
public class PivotesRestController {

    private final GestionarPivotesCUIntPort objGestionarPivotesCUInt;

    @GetMapping("/{symbol}")
    public ResponseEntity<?> obtenerPivots(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "14") int atrLength,
            @RequestParam(defaultValue = "0.1") float slipRatioPct,
            @RequestParam(defaultValue = "2") int longitudVelas,
            @RequestParam(defaultValue = "5") int aniosHistorico,
            @RequestParam(defaultValue = "1") int numeroPivotes) {
        PivotesEncontrados pivotes;
        try {
            pivotes = this.objGestionarPivotesCUInt.obtenerPivots(
                    symbol, atrLength, slipRatioPct, longitudVelas, aniosHistorico, numeroPivotes);
        } catch (PivotesNoDisponiblesException e) {
            return ResponseEntity.status(502).body(Map.of("mensaje", e.getMessage()));
        }
        if (pivotes == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new PivotesDTORespuesta(
                pivotes.getSymbol(),
                pivotes.getCurrentPrice(),
                pivotes.getTimeframe(),
                mapearNiveles(pivotes.getResistances()),
                mapearNiveles(pivotes.getSupports())));
    }

    private List<PivotLevelDTORespuesta> mapearNiveles(List<com.metradingplat.scanner_management.domain.models.PivotLevel> niveles) {
        return niveles.stream()
                .map(nivel -> new PivotLevelDTORespuesta(nivel.getTimestamp(), nivel.getPrice()))
                .collect(Collectors.toList());
    }
}

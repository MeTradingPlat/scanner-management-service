package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarCalendario.controller;

import com.metradingplat.scanner_management.application.input.GestionarCalendarioCUIntPort;
import com.metradingplat.scanner_management.domain.models.EstadoCalendario;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarCalendario.DTOAnswer.EstadoCalendarioDTORespuesta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/escaner/calendario")
@RequiredArgsConstructor
public class CalendarioRestController {

    private final GestionarCalendarioCUIntPort objGestionarCalendarioCUInt;

    @GetMapping
    public ResponseEntity<EstadoCalendarioDTORespuesta> obtenerEstadoCalendario() {
        EstadoCalendario estado = this.objGestionarCalendarioCUInt.obtenerEstadoCalendario();
        if (estado == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new EstadoCalendarioDTORespuesta(estado.isHoyEsDiaHabil(), estado.getProximoDiaHabil()));
    }
}

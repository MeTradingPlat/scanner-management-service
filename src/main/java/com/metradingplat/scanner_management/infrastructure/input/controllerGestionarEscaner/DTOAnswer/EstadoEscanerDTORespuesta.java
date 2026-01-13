package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEscanerDTORespuesta {
    private EnumEstadoEscaner enumEstadoEscaner;
    private LocalDate fechaRegistro;
}

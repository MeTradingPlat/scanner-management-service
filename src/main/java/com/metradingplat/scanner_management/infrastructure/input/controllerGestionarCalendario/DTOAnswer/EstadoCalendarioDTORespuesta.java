package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarCalendario.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCalendarioDTORespuesta {
    private boolean hoyEsDiaHabil;
    private LocalDate proximoDiaHabil;
}

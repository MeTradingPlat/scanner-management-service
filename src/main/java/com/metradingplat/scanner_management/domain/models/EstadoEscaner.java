package com.metradingplat.scanner_management.domain.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEscaner {
    private EnumEstadoEscaner enumEstadoEscaner = EnumEstadoEscaner.DETENIDO;
    private LocalDate fechaRegistro;
}

package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCalendario {
    private boolean hoyEsDiaHabil;
    private LocalDate proximoDiaHabil;
}

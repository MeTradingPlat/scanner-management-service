package com.metradingplat.scanner_management.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PivotLevel {
    private LocalDateTime timestamp;
    private BigDecimal price;
    // "strong" | "weak" -- fuerte cae del lado correcto del precio actual,
    // debil es el relleno automatico cuando faltan fuertes (ver
    // pivots_finder.py). Se muestran por separado en el chart, no fusionados.
    private String strength;
}

package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarPivotes.DTOAnswer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PivotLevelDTORespuesta {
    private LocalDateTime timestamp;
    private BigDecimal price;
    private String strength;
}

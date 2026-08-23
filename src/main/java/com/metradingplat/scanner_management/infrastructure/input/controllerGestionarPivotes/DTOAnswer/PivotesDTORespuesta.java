package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarPivotes.DTOAnswer;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PivotesDTORespuesta {
    private String symbol;
    private BigDecimal currentPrice;
    private String timeframe;
    private List<PivotLevelDTORespuesta> resistances;
    private List<PivotLevelDTORespuesta> supports;
}

package com.metradingplat.scanner_management.domain.models;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PivotesEncontrados {
    private String symbol;
    private BigDecimal currentPrice;
    private String timeframe;
    private List<PivotLevel> resistances;
    private List<PivotLevel> supports;
}

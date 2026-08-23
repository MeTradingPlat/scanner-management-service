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
}

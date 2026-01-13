package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mercado {
    private String etiqueta;
    private EnumMercado enumMercado;
}

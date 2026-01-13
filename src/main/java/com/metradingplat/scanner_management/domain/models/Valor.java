package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Valor {
    private String etiqueta;
    private EnumTipoValor enumTipoValor;
}

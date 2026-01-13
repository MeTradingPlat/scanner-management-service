package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumTipoEjecucion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoEjecucion {
    private String etiqueta;
    private EnumTipoEjecucion enumTipoEjecucion;
}

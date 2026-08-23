package com.metradingplat.scanner_management.domain.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicadorSalida {
    private EnumIndicadorSalida enumIndicadorSalida;
    private String etiquetaNombre;
    private String etiquetaDescripcion;
    private List<ParametroIndicadorSalida> parametros;
}

package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValorStringDTORespuesta extends ValorDTORespuesta {
    private String valor;

    public ValorStringDTORespuesta(EnumTipoValor enumTipoValor, String etiqueta, String valor) {
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }
}

package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;

@Getter
@Setter
@NoArgsConstructor
public class ValorBooleanDTORespuesta extends ValorDTORespuesta {
    private Boolean valor;

    public ValorBooleanDTORespuesta(EnumTipoValor enumTipoValor, String etiqueta, Boolean valor) {
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }
}

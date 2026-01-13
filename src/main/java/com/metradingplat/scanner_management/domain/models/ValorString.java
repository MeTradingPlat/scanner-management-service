package com.metradingplat.scanner_management.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ValorString extends Valor {
    private String valor;

    public ValorString(String etiqueta, EnumTipoValor enumTipoValor, String valor) {
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }

    public ValorString() {
        super();
    }
}

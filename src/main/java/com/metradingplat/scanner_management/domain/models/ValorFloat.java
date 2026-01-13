package com.metradingplat.scanner_management.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class ValorFloat extends Valor {
    private Float valor;

    public ValorFloat(String etiqueta, EnumTipoValor enumTipoValor, Float valor) {
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }

    public ValorFloat() {
        super();
    }
}

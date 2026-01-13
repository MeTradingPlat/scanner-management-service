package com.metradingplat.scanner_management.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class ValorCondicional extends Valor {
    private EnumCondicional enumCondicional;
    private Boolean isInteger = false;
    private Number valor1;
    private Number valor2;

    public ValorCondicional(String etiqueta, EnumTipoValor enumTipoValor, EnumCondicional enumCondicional,
            Number valor1, Number valor2) {
        super(etiqueta, enumTipoValor);
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.enumCondicional = enumCondicional;
    }

    public ValorCondicional(String etiqueta, EnumTipoValor enumTipoValor, EnumCondicional enumCondicional,
            Boolean isInteger, Number valor1, Number valor2) {
        super(etiqueta, enumTipoValor);
        this.isInteger = isInteger;
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.enumCondicional = enumCondicional;
    }

    public ValorCondicional() {
        super();
    }
}

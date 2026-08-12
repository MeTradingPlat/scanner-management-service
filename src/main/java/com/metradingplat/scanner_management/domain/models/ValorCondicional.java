package com.metradingplat.scanner_management.domain.models;

import java.util.List;

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
    // Cuando la estrategia solo puede devolver un conjunto discreto de
    // valores (ej. -1/0/1 para patrones detectados), el frontend usa esto
    // para mostrar un selector con su etiqueta en vez de un campo numerico
    // libre -- null para filtros con salida continua (rango normal de
    // valor1/valor2).
    private List<OpcionValor> valoresPermitidos;

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

package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValorCondicionalDTORespuesta extends ValorDTORespuesta {
    private EnumCondicional enumCondicional;
    private Boolean isInteger;
    private Number valor1;
    private Number valor2;
    private List<OpcionValorDTORespuesta> valoresPermitidos;

    public ValorCondicionalDTORespuesta(EnumTipoValor enumTipoValor, String etiqueta, EnumCondicional enumCondicional,
            Boolean isInteger, Number valor1, Number valor2) {
        super(etiqueta, enumTipoValor);
        this.enumCondicional = enumCondicional;
        this.isInteger = isInteger;
        this.valor1 = valor1;
        this.valor2 = valor2;
    }
}

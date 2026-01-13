package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MercadoDTORespuesta {
    private String etiqueta;
    private EnumMercado enumMercado;
}

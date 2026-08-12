package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Un valor numerico discreto que una estrategia puede devolver, con su
 * etiqueta legible. etiquetaClave es la llave de mensaje sin traducir;
 * etiqueta es el texto ya resuelto por el locale de la peticion.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpcionValor {
    private Number valor;
    private String etiquetaClave;
    private String etiqueta;
}

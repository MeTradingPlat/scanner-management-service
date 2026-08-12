package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import java.util.List;

import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorString;

/**
 * Filtros cuya estrategia solo puede devolver un conjunto discreto y
 * acotado de valores (patron detectado o no, alcista/bajista/ninguno) no
 * tienen sentido con "mayor que"/"entre"/etc -- la unica condicion valida
 * es comparar por igualdad contra uno de esos valores exactos.
 */
final class CondicionalOpciones {
    private CondicionalOpciones() {
    }

    static final List<Number> TERNARIO = List.of(-1F, 0F, 1F);
    static final List<Number> BINARIO = List.of(0F, 1F);

    static List<Valor> soloIgualA() {
        return List.of(new ValorString(
                EnumCondicional.IGUAL_A.getEtiqueta(), EnumTipoValor.STRING, EnumCondicional.IGUAL_A.name()));
    }
}

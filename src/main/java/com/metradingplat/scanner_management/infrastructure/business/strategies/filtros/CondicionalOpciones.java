package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import java.util.List;

import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.models.OpcionValor;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorString;

/**
 * Filtros cuya estrategia solo puede devolver un conjunto discreto y
 * acotado de valores (patron detectado o no, alcista/bajista/ninguno) no
 * tienen sentido con "mayor que"/"entre"/etc -- la unica condicion valida
 * es comparar por igualdad contra uno de esos valores exactos, y cada uno
 * necesita su propia etiqueta (el mismo -1/0/1 significa cosas distintas
 * segun el filtro).
 */
final class CondicionalOpciones {
    private CondicionalOpciones() {
    }

    static List<Valor> soloIgualA() {
        return List.of(new ValorString(
                EnumCondicional.IGUAL_A.getEtiqueta(), EnumTipoValor.STRING, EnumCondicional.IGUAL_A.name()));
    }

    /** Filtros con su propio selector de direccion (TIPO_PATRON/TIPO_VELA):
     * 1 = coincide con lo pedido, -1 = se detecto el patron opuesto, 0 = ninguno. */
    static List<OpcionValor> deteccionConDireccionPropia() {
        return List.of(
                new OpcionValor(1F, "condition.true", null),
                new OpcionValor(0F, "condition.false", null),
                new OpcionValor(-1F, "valueLabel.opposite", null));
    }

    /** Filtros sin selector de direccion donde 1/-1 son racha alcista/bajista. */
    static List<OpcionValor> alcistaBajistaNinguna() {
        return List.of(
                new OpcionValor(1F, "direction.bullish", null),
                new OpcionValor(-1F, "direction.bearish", null),
                new OpcionValor(0F, "valueLabel.none", null));
    }

    /** Filtros sin selector de direccion donde 1/-1 son un extremo arriba/abajo. */
    static List<OpcionValor> extremoArribaAbajoNinguno() {
        return List.of(
                new OpcionValor(1F, "direction.above", null),
                new OpcionValor(-1F, "direction.below", null),
                new OpcionValor(0F, "valueLabel.none", null));
    }

    /** Filtros binarios (solo 0/1, sin caso "opuesto"). */
    static List<OpcionValor> siNo() {
        return List.of(
                new OpcionValor(1F, "condition.true", null),
                new OpcionValor(0F, "condition.false", null));
    }
}

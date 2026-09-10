package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicionFirstCandle;
import com.metradingplat.scanner_management.domain.enums.valores.EnumTimeframe;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;
import com.metradingplat.scanner_management.domain.models.ValorFloat;
import com.metradingplat.scanner_management.domain.models.ValorString;

import com.metradingplat.scanner_management.infrastructure.business.strategies.IFiltroFactory;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;
import com.metradingplat.scanner_management.infrastructure.business.validation.ValidadorParametroFiltro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FiltroFactoryConfirmationCandle implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.CONFIRMATION_CANDLE;
        private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.TIEMPO_Y_PATRONES_DE_PRECIO;
        private final ValidadorParametroFiltro objValidador;

        @Override
        public EnumFiltro obtenerEnumFiltro() {
                return this.enumFiltro;
        }

        @Override
        public EnumCategoriaFiltro obtenerEnumCategoria() {
                return this.enumCategoria;
        }

        @Override
        public Filtro obtenerFiltro() {
                return this.obtenerFiltro(new HashMap<>());
        }

        @Override
        public Filtro obtenerInformacionFiltro() {
                Filtro filtro = new Filtro();
                filtro.setEnumFiltro(this.enumFiltro);
                filtro.setEtiquetaNombre(this.enumFiltro.getEtiquetaNombre());
                filtro.setEtiquetaDescripcion(this.enumFiltro.getEtiquetaDescripcion());

                CategoriaFiltro objCategoriaFiltro = new CategoriaFiltro();
                objCategoriaFiltro.setEnumCategoriaFiltro(this.enumCategoria);
                objCategoriaFiltro.setEtiqueta(this.enumCategoria.getEtiqueta());

                filtro.setObjCategoria(objCategoriaFiltro);

                return filtro;
        }

        @Override
        public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados) {
                Filtro filtro = this.obtenerInformacionFiltro();

                List<Parametro> parametros = new ArrayList<>();
                parametros.add(this.crearParametroTimeframe(
                                (ValorString) valoresSeleccionados.get(EnumParametro.TIMEFRAME_CONFIRMATION_CANDLE)));
                parametros.add(this.crearParametroProporcionCuerpoMinima(
                                (ValorFloat) valoresSeleccionados.get(EnumParametro.PROPORCION_CUERPO_MINIMA_CONFIRMATION_CANDLE)));
                parametros.add(this.crearParametroDireccion(
                                (ValorString) valoresSeleccionados.get(EnumParametro.DIRECCION_CONFIRMATION_CANDLE)));
                parametros.add(crearParametroCondicion(
                                (ValorCondicional) valoresSeleccionados.get(EnumParametro.CONDICION)));

                filtro.setParametros(parametros);
                return filtro;
        }

        private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
                return Arrays.stream(enumValores)
                                .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
                                .collect(Collectors.toList());
        }

        // Pensado para el ultimo tramo del embudo (M1/M3) -- ver
        // signal-processing-service Fase 3 (revisionTiempoReal).
        private static final List<EnumTimeframe> TIMEFRAMES_SOPORTADOS = Arrays.asList(
                EnumTimeframe._1M, EnumTimeframe._3M, EnumTimeframe._5M);

        private Parametro crearParametroTimeframe(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = TIMEFRAMES_SOPORTADOS.stream()
                                .map(e -> new ValorString(e.getEtiqueta(), enumTipoValor, e.getName()))
                                .collect(Collectors.toList());
                EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor())
                                : EnumTimeframe._1M;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.TIMEFRAME_CONFIRMATION_CANDLE,
                                EnumParametro.TIMEFRAME_CONFIRMATION_CANDLE.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroProporcionCuerpoMinima(ValorFloat valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorFloat valor = new ValorFloat(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 0.7F);
                return new Parametro(EnumParametro.PROPORCION_CUERPO_MINIMA_CONFIRMATION_CANDLE,
                                EnumParametro.PROPORCION_CUERPO_MINIMA_CONFIRMATION_CANDLE.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroDireccion(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = this.obtenerOpciones(EnumCondicionFirstCandle.values());
                EnumCondicionFirstCandle enumValor = valorUsuario != null
                                ? EnumCondicionFirstCandle.valueOf(valorUsuario.getValor())
                                : EnumCondicionFirstCandle.ALCISTA;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.DIRECCION_CONFIRMATION_CANDLE,
                                EnumParametro.DIRECCION_CONFIRMATION_CANDLE.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroCondicion(ValorCondicional valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.CONDICIONAL;
                List<Valor> opciones = CondicionalOpciones.soloIgualA();
                ValorCondicional valor = new ValorCondicional(
                                EnumCondicional.IGUAL_A.getEtiqueta(),
                                enumTipoValor,
                                EnumCondicional.IGUAL_A,
                                valorUsuario != null && valorUsuario.getIsInteger() != null
                                                ? valorUsuario.getIsInteger()
                                                : false,
                                valorUsuario != null ? valorUsuario.getValor1() : 1F,
                                valorUsuario != null ? valorUsuario.getValor2() : 1F);
                valor.setValoresPermitidos(CondicionalOpciones.siNo());
                return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
        }

        @Override
        public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
                List<ResultadoValidacion> errores = new ArrayList<>();

                this.objValidador
                                .validarCondicional(this.enumFiltro, EnumParametro.CONDICION,
                                                valoresSeleccionados.get(EnumParametro.CONDICION), 0.0F, 1.0F)
                                .ifPresent(errores::add);

                this.objValidador.validarStringConOpciones(this.enumFiltro,
                                EnumParametro.TIMEFRAME_CONFIRMATION_CANDLE,
                                valoresSeleccionados.get(EnumParametro.TIMEFRAME_CONFIRMATION_CANDLE),
                                TIMEFRAMES_SOPORTADOS)
                                .ifPresent(errores::add);

                this.objValidador.validarFloat(this.enumFiltro,
                                EnumParametro.PROPORCION_CUERPO_MINIMA_CONFIRMATION_CANDLE,
                                valoresSeleccionados.get(EnumParametro.PROPORCION_CUERPO_MINIMA_CONFIRMATION_CANDLE),
                                0.3F, 1.0F)
                                .ifPresent(errores::add);

                this.objValidador.validarString(this.enumFiltro, EnumParametro.DIRECCION_CONFIRMATION_CANDLE,
                                valoresSeleccionados.get(EnumParametro.DIRECCION_CONFIRMATION_CANDLE),
                                EnumCondicionFirstCandle.class)
                                .ifPresent(errores::add);

                return errores;
        }
}

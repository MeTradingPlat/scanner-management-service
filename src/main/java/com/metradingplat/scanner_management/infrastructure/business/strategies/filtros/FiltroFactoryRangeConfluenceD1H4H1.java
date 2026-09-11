package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicionFirstCandle;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;
import com.metradingplat.scanner_management.domain.models.ValorFloat;
import com.metradingplat.scanner_management.domain.models.ValorInteger;
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

// Sin parametro de timeframe: a diferencia del resto de los filtros de esta
// categoria, D1/H4/H1 quedan implicitos -- asi lo describe el curso
// ("Rangos D1 H4 y H1"), no es una eleccion del usuario.
@Component
@RequiredArgsConstructor
public class FiltroFactoryRangeConfluenceD1H4H1 implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.RANGE_CONFLUENCE_D1_H4_H1;
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
                parametros.add(this.crearParametroLookbackVelas(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.LOOKBACK_VELAS_RANGE_CONFLUENCE_D1_H4_H1)));
                parametros.add(this.crearParametroConfirmacionVelas(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.CONFIRMACION_VELAS_RANGE_CONFLUENCE_D1_H4_H1)));
                parametros.add(this.crearParametroProporcionProximidad(
                                (ValorFloat) valoresSeleccionados.get(EnumParametro.PROPORCION_PROXIMIDAD_RANGE_CONFLUENCE_D1_H4_H1)));
                parametros.add(this.crearParametroDireccion(
                                (ValorString) valoresSeleccionados.get(EnumParametro.DIRECCION_RANGE_CONFLUENCE_D1_H4_H1)));
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

        private Parametro crearParametroLookbackVelas(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 20);
                return new Parametro(EnumParametro.LOOKBACK_VELAS_RANGE_CONFLUENCE_D1_H4_H1,
                                EnumParametro.LOOKBACK_VELAS_RANGE_CONFLUENCE_D1_H4_H1.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroConfirmacionVelas(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 2);
                return new Parametro(EnumParametro.CONFIRMACION_VELAS_RANGE_CONFLUENCE_D1_H4_H1,
                                EnumParametro.CONFIRMACION_VELAS_RANGE_CONFLUENCE_D1_H4_H1.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroProporcionProximidad(ValorFloat valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorFloat valor = new ValorFloat(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 0.15F);
                return new Parametro(EnumParametro.PROPORCION_PROXIMIDAD_RANGE_CONFLUENCE_D1_H4_H1,
                                EnumParametro.PROPORCION_PROXIMIDAD_RANGE_CONFLUENCE_D1_H4_H1.getEtiqueta(), valor, opciones);
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
                return new Parametro(EnumParametro.DIRECCION_RANGE_CONFLUENCE_D1_H4_H1,
                                EnumParametro.DIRECCION_RANGE_CONFLUENCE_D1_H4_H1.getEtiqueta(), valor, opciones);
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

                this.objValidador.validarInteger(this.enumFiltro, EnumParametro.LOOKBACK_VELAS_RANGE_CONFLUENCE_D1_H4_H1,
                                valoresSeleccionados.get(EnumParametro.LOOKBACK_VELAS_RANGE_CONFLUENCE_D1_H4_H1), 5, 200)
                                .ifPresent(errores::add);

                this.objValidador.validarInteger(this.enumFiltro, EnumParametro.CONFIRMACION_VELAS_RANGE_CONFLUENCE_D1_H4_H1,
                                valoresSeleccionados.get(EnumParametro.CONFIRMACION_VELAS_RANGE_CONFLUENCE_D1_H4_H1), 1, 10)
                                .ifPresent(errores::add);

                this.objValidador.validarFloat(this.enumFiltro, EnumParametro.PROPORCION_PROXIMIDAD_RANGE_CONFLUENCE_D1_H4_H1,
                                valoresSeleccionados.get(EnumParametro.PROPORCION_PROXIMIDAD_RANGE_CONFLUENCE_D1_H4_H1),
                                0.01F, 0.5F)
                                .ifPresent(errores::add);

                this.objValidador.validarString(this.enumFiltro, EnumParametro.DIRECCION_RANGE_CONFLUENCE_D1_H4_H1,
                                valoresSeleccionados.get(EnumParametro.DIRECCION_RANGE_CONFLUENCE_D1_H4_H1),
                                EnumCondicionFirstCandle.class)
                                .ifPresent(errores::add);

                return errores;
        }
}

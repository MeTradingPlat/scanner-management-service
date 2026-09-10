package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.enums.valores.EnumTimeframe;
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

@Component
@RequiredArgsConstructor
public class FiltroFactoryAccelerationDeceleration implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.ACCELERATION_DECELERATION;
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
                                (ValorString) valoresSeleccionados.get(EnumParametro.TIMEFRAME_ACCELERATION_DECELERATION)));
                parametros.add(this.crearParametroVelasAceleracion(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.VELAS_ACELERACION_ACCELERATION_DECELERATION)));
                parametros.add(this.crearParametroVelasDesaceleracion(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.VELAS_DESACELERACION_ACCELERATION_DECELERATION)));
                parametros.add(this.crearParametroProporcionDesaceleracion(
                                (ValorFloat) valoresSeleccionados.get(EnumParametro.PROPORCION_DESACELERACION_ACCELERATION_DECELERATION)));
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

        private static final List<EnumTimeframe> TIMEFRAMES_SOPORTADOS = Arrays.asList(
                EnumTimeframe._1M, EnumTimeframe._3M, EnumTimeframe._5M, EnumTimeframe._15M,
                EnumTimeframe._30M, EnumTimeframe._1H);

        private Parametro crearParametroTimeframe(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = TIMEFRAMES_SOPORTADOS.stream()
                                .map(e -> new ValorString(e.getEtiqueta(), enumTipoValor, e.getName()))
                                .collect(Collectors.toList());
                EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor())
                                : EnumTimeframe._15M;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.TIMEFRAME_ACCELERATION_DECELERATION,
                                EnumParametro.TIMEFRAME_ACCELERATION_DECELERATION.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroVelasAceleracion(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 4);
                return new Parametro(EnumParametro.VELAS_ACELERACION_ACCELERATION_DECELERATION,
                                EnumParametro.VELAS_ACELERACION_ACCELERATION_DECELERATION.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroVelasDesaceleracion(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 2);
                return new Parametro(EnumParametro.VELAS_DESACELERACION_ACCELERATION_DECELERATION,
                                EnumParametro.VELAS_DESACELERACION_ACCELERATION_DECELERATION.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroProporcionDesaceleracion(ValorFloat valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorFloat valor = new ValorFloat(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 0.4F);
                return new Parametro(EnumParametro.PROPORCION_DESACELERACION_ACCELERATION_DECELERATION,
                                EnumParametro.PROPORCION_DESACELERACION_ACCELERATION_DECELERATION.getEtiqueta(), valor,
                                opciones);
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
                                EnumParametro.TIMEFRAME_ACCELERATION_DECELERATION,
                                valoresSeleccionados.get(EnumParametro.TIMEFRAME_ACCELERATION_DECELERATION),
                                TIMEFRAMES_SOPORTADOS)
                                .ifPresent(errores::add);

                this.objValidador.validarInteger(this.enumFiltro, EnumParametro.VELAS_ACELERACION_ACCELERATION_DECELERATION,
                                valoresSeleccionados.get(EnumParametro.VELAS_ACELERACION_ACCELERATION_DECELERATION), 2, 20)
                                .ifPresent(errores::add);

                this.objValidador.validarInteger(this.enumFiltro,
                                EnumParametro.VELAS_DESACELERACION_ACCELERATION_DECELERATION,
                                valoresSeleccionados.get(EnumParametro.VELAS_DESACELERACION_ACCELERATION_DECELERATION), 1, 10)
                                .ifPresent(errores::add);

                this.objValidador.validarFloat(this.enumFiltro,
                                EnumParametro.PROPORCION_DESACELERACION_ACCELERATION_DECELERATION,
                                valoresSeleccionados.get(EnumParametro.PROPORCION_DESACELERACION_ACCELERATION_DECELERATION),
                                0.05F, 1.0F)
                                .ifPresent(errores::add);

                return errores;
        }
}

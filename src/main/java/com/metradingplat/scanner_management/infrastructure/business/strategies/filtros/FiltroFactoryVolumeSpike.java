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
public class FiltroFactoryVolumeSpike implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.VOLUME_SPIKE;
        private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.VOLUMEN;
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
                return obtenerFiltro(new HashMap<>());
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
                parametros.add(crearParametroCondicion(
                                (ValorCondicional) valoresSeleccionados.get(EnumParametro.CONDICION)));
                parametros.add(crearParametroNumeroVelas(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.NUMERO_VELAS_VOLUME_SPIKE)));
                parametros.add(
                                crearParametroTimeframe((ValorString) valoresSeleccionados
                                                .get(EnumParametro.TIMEFRAME_VOLUME_SPIKE)));
                parametros.add(crearParametroPoporcionVolumen(
                                (ValorFloat) valoresSeleccionados.get(EnumParametro.PROPORCION_VOLUMEN_VOLUME_SPIKE)));

                filtro.setParametros(parametros);
                return filtro;
        }

        private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
                return Arrays.stream(enumValores)
                                .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
                                .collect(Collectors.toList());
        }

        private Parametro crearParametroCondicion(ValorCondicional valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.CONDICIONAL;
                List<Valor> opciones = this.obtenerOpciones(EnumCondicional.values());
                EnumCondicional enumCondicional = valorUsuario != null ? valorUsuario.getEnumCondicional()
                                : EnumCondicional.MAYOR_QUE;
                ValorCondicional valor = new ValorCondicional(
                                enumCondicional.getEtiqueta(),
                                enumTipoValor,
                                enumCondicional,
                                valorUsuario != null && valorUsuario.getIsInteger() != null
                                                ? valorUsuario.getIsInteger()
                                                : false,
                                valorUsuario != null ? valorUsuario.getValor1() : 10F,
                                valorUsuario != null ? valorUsuario.getValor2() : 20F);
                return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroNumeroVelas(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 5);
                return new Parametro(EnumParametro.NUMERO_VELAS_VOLUME_SPIKE,
                                EnumParametro.NUMERO_VELAS_VOLUME_SPIKE.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroTimeframe(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = this.obtenerOpciones(EnumTimeframe.values());
                EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor())
                                : EnumTimeframe._5M;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.TIMEFRAME_VOLUME_SPIKE,
                                EnumParametro.TIMEFRAME_VOLUME_SPIKE.getEtiqueta(),
                                valor, opciones);
        }

        private Parametro crearParametroPoporcionVolumen(ValorFloat valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorFloat valor = new ValorFloat(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 1.5F);
                return new Parametro(EnumParametro.PROPORCION_VOLUMEN_VOLUME_SPIKE,
                                EnumParametro.PROPORCION_VOLUMEN_VOLUME_SPIKE.getEtiqueta(), valor, opciones);
        }

        @Override
        public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
                List<ResultadoValidacion> errores = new ArrayList<>();

                this.objValidador
                                .validarCondicional(this.enumFiltro, EnumParametro.CONDICION,
                                                valoresSeleccionados.get(EnumParametro.CONDICION), 1.0F, 20.0F)
                                .ifPresent(errores::add);

                this.objValidador.validarInteger(this.enumFiltro, EnumParametro.NUMERO_VELAS_VOLUME_SPIKE,
                                valoresSeleccionados.get(EnumParametro.NUMERO_VELAS_VOLUME_SPIKE), 5, 50)
                                .ifPresent(errores::add);

                List<EnumTimeframe> allowedTimeframesVolumeSpike = Arrays.asList(
                                EnumTimeframe._2M, EnumTimeframe._3M, EnumTimeframe._5M, EnumTimeframe._10M,
                                EnumTimeframe._15M, EnumTimeframe._30M, EnumTimeframe._45M, EnumTimeframe._1H,
                                EnumTimeframe._2H, EnumTimeframe._3H, EnumTimeframe._4H, EnumTimeframe._12H,
                                EnumTimeframe._1D);
                this.objValidador.validarStringConOpciones(this.enumFiltro, EnumParametro.TIMEFRAME_VOLUME_SPIKE,
                                valoresSeleccionados.get(EnumParametro.TIMEFRAME_VOLUME_SPIKE),
                                allowedTimeframesVolumeSpike)
                                .ifPresent(errores::add);

                this.objValidador.validarFloat(this.enumFiltro, EnumParametro.PROPORCION_VOLUMEN_VOLUME_SPIKE,
                                valoresSeleccionados.get(EnumParametro.PROPORCION_VOLUMEN_VOLUME_SPIKE), 1.0F, 20.0F)
                                .ifPresent(errores::add);

                return errores;
        }

}

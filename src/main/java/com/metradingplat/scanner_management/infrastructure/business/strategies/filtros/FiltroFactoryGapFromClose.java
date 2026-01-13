package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.enums.valores.EnumTipoValorMedida;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;
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
public class FiltroFactoryGapFromClose implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.GAP_FROM_CLOSE;
        private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO;
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
                parametros.add(
                                this.crearParametroCondicion(
                                                (ValorCondicional) valoresSeleccionados.get(EnumParametro.CONDICION)));
                parametros.add(this
                                .crearParametroFormato((ValorString) valoresSeleccionados
                                                .get(EnumParametro.FORMATO_GAP_FROM_CLOSE)));

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
                                valorUsuario != null ? valorUsuario.getValor1() : 0.10F,
                                valorUsuario != null ? valorUsuario.getValor2() : 10.0F);
                return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroFormato(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = this.obtenerOpciones(EnumTipoValorMedida.values());
                EnumTipoValorMedida enumValor = valorUsuario != null
                                ? EnumTipoValorMedida.valueOf(valorUsuario.getValor())
                                : EnumTipoValorMedida.PORCENTAJE;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.FORMATO_GAP_FROM_CLOSE,
                                EnumParametro.FORMATO_GAP_FROM_CLOSE.getEtiqueta(),
                                valor, opciones);
        }

        @Override
        public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
                List<ResultadoValidacion> errores = new ArrayList<>();

                this.objValidador
                                .validarCondicional(this.enumFiltro, EnumParametro.CONDICION,
                                                valoresSeleccionados.get(EnumParametro.CONDICION), 0.0F, 500.0F)
                                .ifPresent(errores::add);

                this.objValidador.validarString(this.enumFiltro, EnumParametro.FORMATO_GAP_FROM_CLOSE,
                                valoresSeleccionados.get(EnumParametro.FORMATO_GAP_FROM_CLOSE),
                                EnumTipoValorMedida.class)
                                .ifPresent(errores::add);

                return errores;
        }
}
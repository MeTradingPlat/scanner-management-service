package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumDireccionRompimiento;
import com.metradingplat.scanner_management.domain.enums.valores.EnumLineaCruce;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
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
public class FiltroFactoryThroughEmaVwapAlert implements IFiltroFactory {
        private final EnumFiltro enumFiltro = EnumFiltro.THROUGH_EMA_VWAP_ALERT;
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
                parametros.add(this.crearParametroLineaCruce(
                                (ValorString) valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE)));
                parametros.add(this.crearParametroPeriodoEma(
                                (ValorInteger) valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA)));
                parametros.add(this.crearParametroDireccionRompimiento(
                                (ValorString) valoresSeleccionados
                                                .get(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO)));

                filtro.setParametros(parametros);
                return filtro;
        }

        private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
                return Arrays.stream(enumValores)
                                .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
                                .collect(Collectors.toList());
        }

        private Parametro crearParametroLineaCruce(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = this.obtenerOpciones(EnumLineaCruce.values());
                EnumLineaCruce enumValor = valorUsuario != null ? EnumLineaCruce.valueOf(valorUsuario.getValor())
                                : EnumLineaCruce.VWAP;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE,
                                EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroPeriodoEma(ValorInteger valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
                List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
                ValorInteger valor = new ValorInteger(
                                "etiqueta.vacia",
                                enumTipoValor,
                                valorUsuario != null ? valorUsuario.getValor() : 2);
                return new Parametro(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA,
                                EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA.getEtiqueta(), valor, opciones);
        }

        private Parametro crearParametroDireccionRompimiento(ValorString valorUsuario) {
                EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
                List<Valor> opciones = this.obtenerOpciones(EnumDireccionRompimiento.values());
                EnumDireccionRompimiento enumValor = valorUsuario != null
                                ? EnumDireccionRompimiento.valueOf(valorUsuario.getValor())
                                : EnumDireccionRompimiento.ABOVE;
                ValorString valor = new ValorString(
                                enumValor.getEtiqueta(),
                                enumTipoValor,
                                enumValor.name());
                return new Parametro(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO,
                                EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO.getEtiqueta(), valor, opciones);
        }

        @Override
        public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
                List<ResultadoValidacion> errores = new ArrayList<>();

                Valor lineaCruceValor = valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE);
                this.objValidador
                                .validarString(this.enumFiltro, EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE,
                                                lineaCruceValor,
                                                EnumLineaCruce.class)
                                .ifPresent(errores::add);

                // Validacion condicional para PERIODO_EMA_THROUGH_EMA_VWAP
                if (lineaCruceValor instanceof ValorString
                                && ((ValorString) lineaCruceValor).getValor()
                                                .equalsIgnoreCase(EnumLineaCruce.EMA.name())) {
                        this.objValidador.validarInteger(this.enumFiltro, EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA,
                                        valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA), 2, 100)
                                        .ifPresent(errores::add);
                }

                this.objValidador.validarString(this.enumFiltro, EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO,
                                valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO),
                                EnumDireccionRompimiento.class)
                                .ifPresent(errores::add);

                return errores;
        }
}
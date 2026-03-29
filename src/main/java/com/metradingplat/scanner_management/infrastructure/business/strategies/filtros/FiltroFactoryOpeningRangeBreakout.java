package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumTimeframe;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
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
public class FiltroFactoryOpeningRangeBreakout implements IFiltroFactory {
    private final EnumFiltro enumFiltro = EnumFiltro.OPENING_RANGE_BREAKOUT;
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
                (ValorString) valoresSeleccionados.get(EnumParametro.TIMEFRAME_OPENING_RANGE_BREAKOUT)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private static final List<EnumTimeframe> TIMEFRAMES_SOPORTADOS = Arrays.asList(
            EnumTimeframe._1M, EnumTimeframe._5M, EnumTimeframe._15M,
            EnumTimeframe._30M, EnumTimeframe._1H);

    private Parametro crearParametroTimeframe(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = TIMEFRAMES_SOPORTADOS.stream()
                .map(e -> new ValorString(e.getEtiqueta(), enumTipoValor, e.getName()))
                .collect(Collectors.toList());
        EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor())
                : EnumTimeframe._5M;
        ValorString valor = new ValorString(
                enumValor.getEtiqueta(),
                enumTipoValor,
                enumValor.name());
        return new Parametro(EnumParametro.TIMEFRAME_OPENING_RANGE_BREAKOUT,
                EnumParametro.TIMEFRAME_OPENING_RANGE_BREAKOUT.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarStringConOpciones(this.enumFiltro, EnumParametro.TIMEFRAME_OPENING_RANGE_BREAKOUT,
                valoresSeleccionados.get(EnumParametro.TIMEFRAME_OPENING_RANGE_BREAKOUT), TIMEFRAMES_SOPORTADOS)
                .ifPresent(errores::add);

        return errores;
    }
}
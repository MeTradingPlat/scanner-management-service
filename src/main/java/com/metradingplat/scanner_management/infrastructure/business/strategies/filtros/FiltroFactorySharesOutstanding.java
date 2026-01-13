package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
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
public class FiltroFactorySharesOutstanding implements IFiltroFactory {
    private final EnumFiltro enumFiltro = EnumFiltro.SHARES_OUTSTANDING;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.CARACTERISTICAS_FUNDAMENTALES;
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
                this.crearParametroCondicion((ValorCondicional) valoresSeleccionados.get(EnumParametro.CONDICION)));

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
                valorUsuario != null && valorUsuario.getIsInteger() != null ? valorUsuario.getIsInteger() : false, // isInteger
                                                                                                                   // =
                                                                                                                   // true
                                                                                                                   // (número
                                                                                                                   // de
                                                                                                                   // acciones
                                                                                                                   // en
                                                                                                                   // circulación)
                valorUsuario != null ? valorUsuario.getValor1() : 10_000_000,
                valorUsuario != null ? valorUsuario.getValor2() : 1_000_000_000);
        return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador
                .validarCondicional(this.enumFiltro, EnumParametro.CONDICION,
                        valoresSeleccionados.get(EnumParametro.CONDICION), 1_000.0F,
                        50_000_000_000_000.0F)
                .ifPresent(errores::add);

        return errores;
    }
}
package com.metradingplat.scanner_management.infrastructure.business.strategies.filtros;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;

import com.metradingplat.scanner_management.infrastructure.business.strategies.IFiltroFactory;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;
import com.metradingplat.scanner_management.infrastructure.business.validation.ValidadorParametroFiltro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FiltroFactoryHalt implements IFiltroFactory {
    private final EnumFiltro enumFiltro = EnumFiltro.HALT;
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

        // Sin ningun parametro (ni siquiera CONDICION), este filtro pasaba
        // incondicionalmente para cualquier simbolo con datos -- HaltStrategy
        // siempre devuelve 0.0/1.0, nunca None, y evaluate_condition() sin
        // CONDICION configurado retorna True de forma incondicional.
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(crearParametroCondicion(
                (ValorCondicional) valoresSeleccionados.get(EnumParametro.CONDICION)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private Parametro crearParametroCondicion(ValorCondicional valorUsuario) {
        // compute_value() solo devuelve 0.0 (no halted) o 1.0 (halted) --
        // IGUAL_A 1 es la unica condicion valida.
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
        valor.setValoresPermitidos(CondicionalOpciones.BINARIO);
        return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador
                .validarCondicional(this.enumFiltro, EnumParametro.CONDICION,
                        valoresSeleccionados.get(EnumParametro.CONDICION), 0.0F, 1.0F)
                .ifPresent(errores::add);

        return errores;
    }
}

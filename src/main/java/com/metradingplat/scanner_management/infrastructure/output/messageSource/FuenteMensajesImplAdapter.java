package com.metradingplat.scanner_management.infrastructure.output.messageSource;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.output.FuenteMensajesIntPort;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EscanerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.TipoEjecucionDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.CategoriaDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.FiltroDtoRespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ParametroDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.DTOAnswer.MercadoDTORespuesta;

@Component
@RequiredArgsConstructor
public class FuenteMensajesImplAdapter implements FuenteMensajesIntPort {

    private final MessageSource messageSource;

    @Override
    public String obtenerMensaje(String llaveMensaje, Locale locale, Object... args) {
        String pattern = this.messageSource.getMessage(llaveMensaje, null, locale);
        return (args != null && args.length > 0)
                ? MessageFormat.format(pattern, args)
                : pattern;
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String internacionalizarMensaje(String llaveMensaje, Object... args) {
        String mensaje = this.obtenerMensaje(llaveMensaje, this.getLocale(), args);
        return mensaje;
    }

    @Override
    public MercadoDTORespuesta internacionalizarMercado(MercadoDTORespuesta objeto) {
        if (objeto == null || objeto.getEnumMercado() == null)
            return objeto;
        objeto.setEtiqueta(this.obtenerMensaje(objeto.getEnumMercado().getEtiqueta(), this.getLocale()));
        return objeto;
    }

    @Override
    public List<MercadoDTORespuesta> internacionalizarMercados(List<MercadoDTORespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarMercado)
                .collect(Collectors.toList());
    }

    @Override
    public EscanerDTORespuesta internacionalizarEscaner(EscanerDTORespuesta objeto) {
        if (objeto == null)
            return null;
        objeto.setMercados(this.internacionalizarMercados(objeto.getMercados()));
        objeto.setObjTipoEjecucion(this.internacionalizarTipoEjecucion(objeto.getObjTipoEjecucion()));
        return objeto;
    }

    @Override
    public List<EscanerDTORespuesta> internacionalizarEscaneres(List<EscanerDTORespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarEscaner)
                .collect(Collectors.toList());
    }

    @Override
    public TipoEjecucionDTORespuesta internacionalizarTipoEjecucion(TipoEjecucionDTORespuesta objeto) {
        if (objeto == null || objeto.getEnumTipoEjecucion() == null)
            return objeto;
        objeto.setEtiqueta(this.obtenerMensaje(objeto.getEnumTipoEjecucion().getEtiqueta(), this.getLocale()));
        return objeto;
    }

    @Override
    public FiltroDtoRespuesta internacionalizarFiltro(FiltroDtoRespuesta objeto) {
        if (objeto == null || objeto.getEnumFiltro() == null)
            return objeto;

        Locale locale = this.getLocale();
        objeto.setEtiquetaNombre(this.obtenerMensaje(objeto.getEnumFiltro().getEtiquetaNombre(), locale));
        objeto.setEtiquetaDescripcion(this.obtenerMensaje(objeto.getEnumFiltro().getEtiquetaDescripcion(), locale));
        objeto.setObjCategoria(this.internacionalizarCategoria(objeto.getObjCategoria()));
        objeto.setParametros(this.internacionalizarParametros(objeto.getParametros()));
        return objeto;
    }

    @Override
    public List<FiltroDtoRespuesta> internacionalizarFiltros(List<FiltroDtoRespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarFiltro)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTORespuesta internacionalizarCategoria(CategoriaDTORespuesta objeto) {
        if (objeto == null || objeto.getEnumCategoriaFiltro() == null)
            return objeto;
        objeto.setEtiqueta(this.obtenerMensaje(objeto.getEnumCategoriaFiltro().getEtiqueta(), this.getLocale()));
        return objeto;
    }

    @Override
    public List<CategoriaDTORespuesta> internacionalizarCategorias(List<CategoriaDTORespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarCategoria)
                .collect(Collectors.toList());
    }

    @Override
    public ParametroDTORespuesta internacionalizarParametro(ParametroDTORespuesta objeto) {
        if (objeto == null || objeto.getEnumParametro() == null)
            return objeto;
        objeto.setEtiqueta(this.obtenerMensaje(objeto.getEnumParametro().getEtiqueta(), this.getLocale()));
        objeto.setObjValorSeleccionado(this.internacionalizarValor(objeto.getObjValorSeleccionado()));
        objeto.setOpciones(this.internacionalizarValores(objeto.getOpciones()));
        return objeto;
    }

    @Override
    public List<ParametroDTORespuesta> internacionalizarParametros(List<ParametroDTORespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarParametro)
                .collect(Collectors.toList());
    }

    @Override
    public ValorDTORespuesta internacionalizarValor(ValorDTORespuesta objeto) {
        if (objeto == null)
            return null;
        objeto.setEtiqueta(this.obtenerMensaje(objeto.getEtiqueta(), this.getLocale()));
        return objeto;
    }

    @Override
    public List<ValorDTORespuesta> internacionalizarValores(List<ValorDTORespuesta> objetos) {
        if (objetos == null || objetos.isEmpty())
            return Collections.emptyList();
        return objetos.stream()
                .map(this::internacionalizarValor)
                .collect(Collectors.toList());
    }
}
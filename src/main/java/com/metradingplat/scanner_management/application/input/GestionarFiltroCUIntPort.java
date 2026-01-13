package com.metradingplat.scanner_management.application.input;

import java.util.List;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;

public interface GestionarFiltroCUIntPort {
    public List<CategoriaFiltro> obtenerCategorias();

    public List<Filtro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro enumCategoria);

    public Filtro obtenerFiltroPorDefecto(EnumFiltro enumFiltro);

    public List<Filtro> obtenerFiltros(Long idEscaner);

    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtrosEscaner);
}
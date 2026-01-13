package com.metradingplat.scanner_management.application.input;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Valor;

import java.util.List;
import java.util.Map;

/**
 * Puerto de entrada para gestionar estrategias de filtros.
 * Define las operaciones disponibles para trabajar con filtros desde la capa de
 * aplicación.
 */
public interface FilterStrategyPort {

    /**
     * Crea un filtro con los valores seleccionados por el usuario.
     * 
     * @param enumFiltro           Tipo de filtro a crear
     * @param valoresSeleccionados Valores de parámetros seleccionados
     * @return Filtro creado con los valores
     */
    Filtro createFilter(EnumFiltro enumFiltro, Map<EnumParametro, Valor> valoresSeleccionados);

    /**
     * Obtiene la información base de un filtro (sin valores de usuario).
     * 
     * @param enumFiltro Tipo de filtro
     * @return Filtro con información por defecto
     */
    Filtro getFilterInfo(EnumFiltro enumFiltro);

    /**
     * Obtiene todos los filtros disponibles con su información base.
     * 
     * @return Lista de todos los filtros disponibles
     */
    List<Filtro> getAllFilters();

    /**
     * Valida los valores seleccionados para un filtro específico.
     * 
     * @param enumFiltro           Tipo de filtro
     * @param valoresSeleccionados Valores a validar
     * @return Lista de errores de validación (vacía si todo es válido)
     */
    List<String> validateFilter(EnumFiltro enumFiltro, Map<EnumParametro, Valor> valoresSeleccionados);
}

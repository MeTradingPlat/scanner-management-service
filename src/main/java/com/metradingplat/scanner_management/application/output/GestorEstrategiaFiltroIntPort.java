package com.metradingplat.scanner_management.application.output;

import java.util.List;
import java.util.Map;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;

/**
 * Port para gestionar estrategias de filtros.
 * Define el contrato para acceder y validar filtros de escáneres.
 */
public interface GestorEstrategiaFiltroIntPort {

    /**
     * Obtiene la lista de filtros disponibles para una categoría específica.
     *
     * @param categoria La categoría de filtros a consultar
     * @return Lista de enums de filtros disponibles en esa categoría
     */
    List<EnumFiltro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro categoria);

    /**
     * Obtiene la información básica de un filtro (nombre, descripción, categoría).
     *
     * @param tipoFiltro El tipo de filtro del cual obtener información
     * @return Objeto Filtro con información básica
     */
    Filtro obtenerInfomracionFiltro(EnumFiltro tipoFiltro);

    /**
     * Obtiene un filtro con sus valores por defecto.
     *
     * @param tipoFiltro El tipo de filtro a crear
     * @return Objeto Filtro con valores por defecto
     */
    Filtro obtenerFiltroConValoresPorDefecto(EnumFiltro tipoFiltro);

    /**
     * Crea un filtro con valores personalizados seleccionados por el usuario.
     *
     * @param tipoFiltro El tipo de filtro a crear
     * @param valores    Mapa de parámetros con sus valores seleccionados
     * @return Objeto Filtro con valores personalizados
     */
    Filtro crearFiltroConValoresSeleccionados(EnumFiltro tipoFiltro, Map<EnumParametro, Valor> valores);

    /**
     * Valida si un enum de filtro es válido y existe en el sistema.
     *
     * @param tipoFiltro El tipo de filtro a validar
     * @return true si el filtro existe, false en caso contrario
     */
    Boolean validarEnumFiltro(EnumFiltro tipoFiltro);

    /**
     * Valida los valores seleccionados para un filtro específico.
     *
     * @param tipoFiltro El tipo de filtro a validar
     * @param valores    Mapa de parámetros con sus valores a validar
     * @return Lista de resultados de validación (vacía si todo es válido)
     */
    List<ResultadoValidacion> validarValoresSeleccionados(EnumFiltro tipoFiltro, Map<EnumParametro, Valor> valores);
}

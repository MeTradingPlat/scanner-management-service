package com.metradingplat.scanner_management.infrastructure.business.strategies;

import java.util.List;
import java.util.Map;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;

/**
 * Interfaz que define el contrato para factorías de filtros específicos.
 *
 * <p>
 * <b>Patrón de Diseño:</b> Abstract Factory Pattern
 *
 * <p>
 * Esta interfaz NO implementa el patrón Strategy a pesar del nombre histórico
 * "Estrategia".
 * Cada implementación es una factoría concreta que sabe cómo crear y configurar
 * un tipo específico de filtro (Precio, Volumen, RSI, etc.).
 *
 * <p>
 * Las implementaciones NO son intercambiables entre sí porque cada una crea
 * un producto diferente (tipo de filtro diferente).
 *
 * <p>
 * <b>Responsabilidades:</b>
 * <ul>
 * <li>Proporcionar metadatos del filtro (enum, categoría)</li>
 * <li>Crear instancias de Filtro con valores por defecto</li>
 * <li>Crear instancias de Filtro con valores personalizados</li>
 * <li>Validar valores seleccionados para el filtro</li>
 * </ul>
 *
 * <p>
 * <b>Implementaciones concretas:</b> Existen 41 implementaciones, una por cada
 * tipo de filtro: {@code FiltroFactoryPrecio}, {@code FiltroFactoryVolume},
 * etc.
 *
 * <p>
 * <b>Uso típico:</b>
 * 
 * <pre>{@code
 * IFiltroFactory factory = new FiltroFactoryPrecio(validador);
 * Filtro filtro = factory.obtenerFiltro(); // Filtro con valores por defecto
 * }</pre>
 *
 * @see GestorFiltroFactory
 */
public interface IFiltroFactory {

    /**
     * Obtiene el tipo de filtro que esta factoría puede crear.
     *
     * @return El enum que identifica el tipo de filtro
     */
    EnumFiltro obtenerEnumFiltro();

    /**
     * Obtiene la categoría a la que pertenece este filtro.
     *
     * @return El enum de categoría del filtro
     */
    EnumCategoriaFiltro obtenerEnumCategoria();

    /**
     * Crea una instancia de Filtro con valores por defecto.
     *
     * @return Filtro configurado con valores por defecto
     */
    Filtro obtenerFiltro();

    /**
     * Obtiene la información básica del filtro (metadatos sin parámetros).
     *
     * @return Filtro con información básica (nombre, descripción, categoría)
     */
    Filtro obtenerInformacionFiltro();

    /**
     * Crea una instancia de Filtro con valores personalizados.
     *
     * @param valoresSeleccionados Mapa de parámetros con valores seleccionados por
     *                             el usuario
     * @return Filtro configurado con los valores proporcionados
     */
    Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados);

    /**
     * Valida los valores seleccionados para este tipo de filtro.
     *
     * @param valoresSeleccionados Mapa de parámetros con valores a validar
     * @return Lista de errores de validación (vacía si todo es válido)
     */
    List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados);
}

package com.metradingplat.scanner_management.infrastructure.business.validation;

import java.util.Optional;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Valor;

/**
 * Interfaz que define el contrato para estrategias de validación de parámetros
 * de filtros.
 *
 * <p>
 * <b>Patrón de Diseño:</b> Strategy Pattern
 *
 * <p>
 * Esta interfaz permite implementar diferentes algoritmos de validación que
 * pueden
 * ser intercambiados en tiempo de ejecución. Cada implementación concreta
 * encapsula
 * un algoritmo específico de validación (Float, Integer, String, etc.).
 *
 * <p>
 * <b>Implementaciones concretas:</b>
 * <ul>
 * <li>{@link ValidacionFloat} - Valida valores Float dentro de un rango</li>
 * <li>{@link ValidacionInteger} - Valida valores Integer dentro de un
 * rango</li>
 * <li>{@link ValidacionString} - Valida valores String contra una
 * enumeración</li>
 * <li>{@link ValidacionStringConOpciones} - Valida valores String contra una
 * lista de opciones</li>
 * <li>{@link ValidacionCondicional} - Valida valores condicionales (rangos con
 * operadores)</li>
 * </ul>
 *
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>{@code
 * IValidacionFiltro estrategia = new ValidacionFloat(0f, 100f);
 * Optional<ResultadoValidacion> resultado = estrategia.validar(EnumFiltro.RSI, parametro, valor);
 *
 * if (resultado.isPresent()) {
 *     // La validación falló
 *     ResultadoValidacion error = resultado.get();
 *     System.out.println("Error: " + error.mensaje());
 * } else {
 *     // La validación fue exitosa
 * }
 * }</pre>
 *
 * @see ValidadorParametroFiltro
 * @see ResultadoValidacion
 */
public interface IValidacionFiltro {

    /**
     * Valida un valor según las reglas específicas de la estrategia implementada.
     *
     * <p>
     * El parámetro enumFiltro se incluye para proporcionar contexto completo
     * en los mensajes de error, permitiendo al frontend identificar exactamente
     * a qué filtro pertenece cada error de validación de parámetro.
     *
     * @param enumFiltro    El filtro al que pertenece el parámetro (contexto del
     *                      error)
     * @param enumParametro El parámetro que se está validando
     * @param valor         El valor a validar
     * @return Optional.empty() si la validación es exitosa,
     *         Optional con ResultadoValidacion si hay errores de validación
     */
    Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor);
}

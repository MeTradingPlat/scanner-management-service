package com.metradingplat.scanner_management.infrastructure.business.validation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Valor;

/**
 * Servicio de validación de parámetros de filtros.
 *
 * <p>
 * Este servicio actúa como un Factory que crea y ejecuta estrategias de
 * validación
 * según el tipo de parámetro a validar. Cada método factory crea la estrategia
 * apropiada y delega la validación a ella.
 *
 * <p>
 * <b>Patrón:</b> Strategy Pattern con Factory Method
 *
 * <p>
 * <b>Uso:</b>
 * 
 * <pre>{@code
 * ValidadorParametroFiltro validador = ...;
 * Optional<ResultadoValidacion> resultado = validador.validarFloat(parametro, valor, 0f, 100f);
 * if (resultado.isPresent()) {
 *     // Validación falló
 * }
 * }</pre>
 *
 * @see IValidacionFiltro
 */
@Service
public class ValidadorParametroFiltro {

    /**
     * Valida un valor condicional (rango numérico con operadores).
     *
     * @param enumFiltro    El filtro al que pertenece el parámetro (contexto del
     *                      error)
     * @param enumParametro El parámetro que se está validando
     * @param valor         El valor a validar
     * @param min           Valor mínimo permitido
     * @param max           Valor máximo permitido
     * @return Optional con ResultadoValidacion si hay error, Optional.empty() si es
     *         válido
     */
    public Optional<ResultadoValidacion> validarCondicional(EnumFiltro enumFiltro, EnumParametro enumParametro,
            Valor valor, Float min, Float max) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaCondicional(min, max);
        return estrategiaValidacion.validar(enumFiltro, enumParametro, valor);
    }

    /**
     * Valida un valor de tipo Float dentro de un rango.
     *
     * @param enumFiltro    El filtro al que pertenece el parámetro (contexto del
     *                      error)
     * @param enumParametro El parámetro que se está validando
     * @param valor         El valor a validar
     * @param min           Valor mínimo permitido
     * @param max           Valor máximo permitido
     * @return Optional con ResultadoValidacion si hay error, Optional.empty() si es
     *         válido
     */
    public Optional<ResultadoValidacion> validarFloat(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor,
            Float min, Float max) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaFloat(min, max);
        return estrategiaValidacion.validar(enumFiltro, enumParametro, valor);
    }

    /**
     * Valida un valor de tipo Integer dentro de un rango.
     *
     * @param enumFiltro    El filtro al que pertenece el parámetro (contexto del
     *                      error)
     * @param enumParametro El parámetro que se está validando
     * @param valor         El valor a validar
     * @param min           Valor mínimo permitido
     * @param max           Valor máximo permitido
     * @return Optional con ResultadoValidacion si hay error, Optional.empty() si es
     *         válido
     */
    public Optional<ResultadoValidacion> validarInteger(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor,
            float min, float max) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaInteger((int) min, (int) max);
        return estrategiaValidacion.validar(enumFiltro, enumParametro, valor);
    }

    /**
     * Valida un valor de tipo String contra una enumeración.
     *
     * @param <E>           Tipo de la enumeración
     * @param enumFiltro    El filtro al que pertenece el parámetro (contexto del
     *                      error)
     * @param enumParametro El parámetro que se está validando
     * @param valor         El valor a validar
     * @param enumOpciones  Clase de la enumeración con valores válidos
     * @return Optional con ResultadoValidacion si hay error, Optional.empty() si es
     *         válido
     */
    public <E extends Enum<E>> Optional<ResultadoValidacion> validarString(EnumFiltro enumFiltro,
            EnumParametro enumParametro, Valor valor, Class<E> enumOpciones) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaString(enumOpciones);
        return estrategiaValidacion.validar(enumFiltro, enumParametro, valor);
    }

    /**
     * Valida un valor de tipo String contra una lista de opciones permitidas.
     *
     * @param <E>                Tipo de la enumeración con valores permitidos
     * @param enumFiltro         El filtro al que pertenece el parámetro (contexto
     *                           del error)
     * @param enumParametro      El parámetro que se está validando
     * @param valor              El valor a validar
     * @param opcionesPermitidas Lista de opciones válidas
     * @return Optional con ResultadoValidacion si hay error, Optional.empty() si es
     *         válido
     */
    public <E extends Enum<E> & IEnumValores> Optional<ResultadoValidacion> validarStringConOpciones(
            EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor, List<E> opcionesPermitidas) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaStringConOpciones(opcionesPermitidas);
        return estrategiaValidacion.validar(enumFiltro, enumParametro, valor);
    }

    // ========== Factory Methods (privados) ==========

    /**
     * Crea una estrategia de validación condicional.
     * Este método encapsula la creación de la estrategia concreta.
     */
    private IValidacionFiltro crearEstrategiaCondicional(Float min, Float max) {
        return new ValidacionCondicional(min, max);
    }

    /**
     * Crea una estrategia de validación para Float.
     * Este método encapsula la creación de la estrategia concreta.
     */
    private IValidacionFiltro crearEstrategiaFloat(Float min, Float max) {
        return new ValidacionFloat(min, max);
    }

    /**
     * Crea una estrategia de validación para Integer.
     * Este método encapsula la creación de la estrategia concreta.
     */
    private IValidacionFiltro crearEstrategiaInteger(int min, int max) {
        return new ValidacionInteger(min, max);
    }

    /**
     * Crea una estrategia de validación para String con enum.
     * Este método encapsula la creación de la estrategia concreta.
     */
    private <E extends Enum<E>> IValidacionFiltro crearEstrategiaString(Class<E> enumOpciones) {
        return new ValidacionString<>(enumOpciones);
    }

    /**
     * Crea una estrategia de validación para String con opciones personalizadas.
     * Este método encapsula la creación de la estrategia concreta.
     */
    private <E extends Enum<E> & IEnumValores> IValidacionFiltro crearEstrategiaStringConOpciones(
            List<E> opcionesPermitidas) {
        return new ValidacionStringConOpciones<>(opcionesPermitidas);
    }
}

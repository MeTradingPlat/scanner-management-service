package com.metradingplat.scanner_management.infrastructure.business.validation;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.valores.EnumCondicional;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;

import java.util.Optional;

/**
 * Estrategia de validación para valores condicionales.
 *
 * <p>
 * Valida que los valores numéricos cumplan con:
 * <ul>
 * <li>Rangos mínimos y máximos permitidos</li>
 * <li>Orden correcto para operadores ENTRE/FUERA</li>
 * <li>Tipo de dato correcto (Integer vs Float)</li>
 * </ul>
 *
 * @see IValidacionFiltro
 */
public class ValidacionCondicional implements IValidacionFiltro {

    private final float min;
    private final float max;

    public ValidacionCondicional(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor) {
        if (!(valor instanceof ValorCondicional vc)) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.type.invalid");
        }

        // Validar que isInteger esté configurado
        if (vc.getIsInteger() == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.isInteger.notSet");
        }

        // Validar tipo de dato según isInteger
        Optional<ResultadoValidacion> errorTipo = validarTipoDato(enumFiltro, vc, enumParametro);
        if (errorTipo.isPresent()) {
            return errorTipo;
        }

        Float v1 = toFloat(vc.getValor1());
        EnumCondicional cond = vc.getEnumCondicional();

        // Validar operadores que requieren dos valores
        if (cond == EnumCondicional.ENTRE || cond == EnumCondicional.FUERA) {
            if (vc.getValor2() == null) {
                return resultado(enumFiltro, enumParametro, "validation.parameter.secondValue.required");
            }

            Float v2 = toFloat(vc.getValor2());

            // Validar orden de valores
            if (cond == EnumCondicional.ENTRE && v1 >= v2) {
                return resultado(enumFiltro, enumParametro, "validation.parameter.values.orderInvalid.between", v1, v2);
            }
            if (cond == EnumCondicional.FUERA && v1 <= v2) {
                return resultado(enumFiltro, enumParametro, "validation.parameter.values.orderInvalid.outside", v1, v2);
            }

            // Validar rango
            if (v1 < min || v2 > max) {
                return resultado(enumFiltro, enumParametro, "validation.parameter.values.outOfRange", min, max);
            }

        } else {
            // Validar rango para operadores de un solo valor
            if (v1 < min || v1 > max) {
                return resultado(enumFiltro, enumParametro, "validation.parameter.values.outOfRange", min, max);
            }
        }

        return Optional.empty();
    }

    /**
     * Valida que el tipo de dato del valor sea compatible con el flag isInteger.
     *
     * <p>
     * <b>Reglas de conversión:</b>
     * <ul>
     * <li>✅ Integer → Float: PERMITIDO (conversión segura, no hay pérdida)</li>
     * <li>❌ Float → Integer: RECHAZADO (pérdida de decimales)</li>
     * </ul>
     *
     * @param vc            ValorCondicional a validar
     * @param enumParametro Parámetro para el mensaje de error
     * @return Optional con error si la conversión no es segura, empty si es válido
     */
    private Optional<ResultadoValidacion> validarTipoDato(EnumFiltro enumFiltro, ValorCondicional vc,
            EnumParametro enumParametro) {
        Number v1 = vc.getValor1();
        Number v2 = vc.getValor2();
        Boolean isInteger = vc.getIsInteger();

        if (isInteger) {
            // Si isInteger=true, NO aceptar Float/Double (pérdida de decimales)
            if (v1 != null && (v1 instanceof Float || v1 instanceof Double)) {
                // Verificar si tiene decimales
                if (tieneDecimales(v1)) {
                    return resultado(enumFiltro, enumParametro, "validation.parameter.type.floatToIntegerLoss");
                }
            }
            if (v2 != null && (v2 instanceof Float || v2 instanceof Double)) {
                // Verificar si tiene decimales
                if (tieneDecimales(v2)) {
                    return resultado(enumFiltro, enumParametro, "validation.parameter.type.floatToIntegerLoss");
                }
            }
        }
        // Si isInteger=false (Float esperado), aceptar CUALQUIER Number (Integer se
        // convierte a Float automáticamente)

        return Optional.empty();
    }

    /**
     * Verifica si un número tiene parte decimal.
     *
     * @param n Número a verificar
     * @return true si tiene decimales, false si es entero
     */
    private boolean tieneDecimales(Number n) {
        double d = n.doubleValue();
        return d != Math.floor(d);
    }

    /**
     * Convierte un Number a Float para comparaciones.
     *
     * @param n Número a convertir
     * @return Valor como Float
     */
    private static float toFloat(Number n) {
        return switch (n) {
            case Integer i -> i.floatValue();
            case Long l -> l.floatValue();
            case Float f -> f;
            case Double d -> d.floatValue();
            default -> n.floatValue();
        };
    }

    /**
     * Crea un ResultadoValidacion con el mensaje y parámetros dados.
     *
     * @param mensaje   Clave del mensaje de error
     * @param parametro Parámetro que falló la validación
     * @param args      Argumentos adicionales para el mensaje
     * @return Optional con el resultado de validación
     */
    private Optional<ResultadoValidacion> resultado(EnumFiltro filtro, EnumParametro parametro, String mensaje,
            Object... args) {
        return Optional.of(new ResultadoValidacion(filtro, parametro, mensaje, args));
    }
}

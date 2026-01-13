package com.metradingplat.scanner_management.infrastructure.business.validation;

import java.util.Optional;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorFloat;

public class ValidacionFloat implements IValidacionFiltro {
    private final Float min;
    private final Float max;

    public ValidacionFloat(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (!(valor instanceof ValorFloat valorFloat)) {
            return resultado(enumFiltro, enumParametro, "validation.format.invalid");
        }

        Float contenido = valorFloat.getValor();

        if (contenido == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (contenido < min || contenido > max) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.values.outOfRange", min, max);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(EnumFiltro filtro, EnumParametro parametro, String mensaje,
            Object... args) {
        return Optional.of(new ResultadoValidacion(filtro, parametro, mensaje, args));
    }
}

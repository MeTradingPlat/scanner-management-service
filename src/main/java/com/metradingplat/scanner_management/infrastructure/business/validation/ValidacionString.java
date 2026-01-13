package com.metradingplat.scanner_management.infrastructure.business.validation;

import java.util.Optional;
import java.util.stream.Stream;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorString;

public class ValidacionString<E extends Enum<E>> implements IValidacionFiltro {
    private final Class<E> enumOpcionesClass;

    public ValidacionString(Class<E> enumOpcionesClass) {
        this.enumOpcionesClass = enumOpcionesClass;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (!(valor instanceof ValorString valorString)) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.type.invalid");
        }

        String contenido = valorString.getValor();

        if (contenido == null || !esValorEnumValido(contenido)) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(EnumFiltro filtro, EnumParametro parametro, String mensaje,
            Object... args) {
        return Optional.of(new ResultadoValidacion(filtro, parametro, mensaje, args));
    }

    private boolean esValorEnumValido(String valor) {
        return Stream.of(enumOpcionesClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(valor));
    }
}

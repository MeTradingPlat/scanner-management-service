package com.metradingplat.scanner_management.infrastructure.business.validation;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorString;

import java.util.List;
import java.util.Optional;

public class ValidacionStringConOpciones<E extends Enum<E> & IEnumValores> implements IValidacionFiltro {

    private final List<E> opcionesPermitidas;

    public ValidacionStringConOpciones(List<E> opcionesPermitidas) {
        this.opcionesPermitidas = opcionesPermitidas;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (!(valor instanceof ValorString valorStr)) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.type.invalid");
        }

        String valorTexto = valorStr.getValor();

        if (valorTexto.trim().isEmpty()) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        boolean esValido = opcionesPermitidas.stream()
                .anyMatch(opcion -> opcion.name().equalsIgnoreCase(valorTexto));

        if (!esValido) {
            return resultado(enumFiltro, enumParametro, "validation.enum.invalid");
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(EnumFiltro filtro, EnumParametro parametro, String mensaje,
            Object... args) {
        return Optional.of(new ResultadoValidacion(filtro, parametro, mensaje, args));
    }
}

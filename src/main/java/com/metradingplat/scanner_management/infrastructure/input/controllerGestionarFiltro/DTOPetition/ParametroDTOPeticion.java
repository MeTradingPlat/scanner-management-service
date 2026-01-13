package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametroDTOPeticion {
    @NotNull(message = "validation.enum.invalid")
    private EnumParametro enumParametro;

    @NotNull(message = "validation.parameter.value.required")
    @Valid
    private ValorDTOPeticion objValorSeleccionado;
}
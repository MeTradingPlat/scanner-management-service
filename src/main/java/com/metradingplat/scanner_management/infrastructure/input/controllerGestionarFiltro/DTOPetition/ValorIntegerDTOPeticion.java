package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValorIntegerDTOPeticion extends ValorDTOPeticion {
    @NotNull(message = "validation.parameter.value.required")
    private Integer valor;
}
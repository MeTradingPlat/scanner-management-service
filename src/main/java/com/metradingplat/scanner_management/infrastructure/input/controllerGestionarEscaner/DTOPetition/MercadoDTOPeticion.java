package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;

@Getter
@Setter
public class MercadoDTOPeticion {
    @NotNull(message = "validation.enum.invalid")
    private EnumMercado enumMercado;
}
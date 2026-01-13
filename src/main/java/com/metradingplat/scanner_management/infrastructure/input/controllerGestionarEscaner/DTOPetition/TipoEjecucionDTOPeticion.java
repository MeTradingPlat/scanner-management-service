package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoEjecucion;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEjecucionDTOPeticion {
    @NotNull(message = "validation.enum.invalid")
    private EnumTipoEjecucion enumTipoEjecucion;
}

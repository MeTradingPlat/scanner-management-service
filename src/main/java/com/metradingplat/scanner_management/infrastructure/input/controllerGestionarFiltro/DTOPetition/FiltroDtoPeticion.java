package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDtoPeticion {
    @NotNull(message = "filtro.enum.empty")
    private EnumFiltro enumFiltro;

    @NotNull(message = "validation.filter.parameters.required")
    @Valid
    private List<ParametroDTOPeticion> parametros;
}

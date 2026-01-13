package com.metradingplat.scanner_management.application.output;

import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoEscaner;

public interface GestionarEstadoEscanerGatewayIntPort {
    public EnumEstadoEscaner obtenerEstadoDeEscanerActual(Long id);

    public EstadoEscaner cambiarEstadoDeEscaner(Escaner escaner, EnumEstadoEscaner nuevoEstadoEnum);
}
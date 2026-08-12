package com.metradingplat.scanner_management.domain.usecases;

import com.metradingplat.scanner_management.application.input.GestionarCalendarioCUIntPort;
import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.domain.models.EstadoCalendario;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GestionarCalendarioCUAdapter implements GestionarCalendarioCUIntPort {

    private final FuenteMensajesSignalProcessingIntPort objFuenteMensajesSignalProcessing;

    @Override
    public EstadoCalendario obtenerEstadoCalendario() {
        return this.objFuenteMensajesSignalProcessing.obtenerEstadoCalendario();
    }
}

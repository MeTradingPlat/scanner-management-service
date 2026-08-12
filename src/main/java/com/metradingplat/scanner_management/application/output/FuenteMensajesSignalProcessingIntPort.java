package com.metradingplat.scanner_management.application.output;

import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoCalendario;

public interface FuenteMensajesSignalProcessingIntPort {
    void notificarEscanerIniciado(Escaner escaner);

    void notificarEscanerDetenido(Long idEscaner);

    EstadoCalendario obtenerEstadoCalendario();
}

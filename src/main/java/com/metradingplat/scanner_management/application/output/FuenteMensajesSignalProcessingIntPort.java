package com.metradingplat.scanner_management.application.output;

import com.metradingplat.scanner_management.domain.models.Escaner;

public interface FuenteMensajesSignalProcessingIntPort {
    void notificarEscanerIniciado(Escaner escaner);

    void notificarEscanerDetenido(Long idEscaner);
}

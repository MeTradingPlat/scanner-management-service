package com.metradingplat.scanner_management.application.output;

import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoCalendario;
import com.metradingplat.scanner_management.domain.models.PivotesEncontrados;

public interface FuenteMensajesSignalProcessingIntPort {
    void notificarEscanerIniciado(Escaner escaner);

    void notificarEscanerDetenido(Long idEscaner);

    EstadoCalendario obtenerEstadoCalendario();

    PivotesEncontrados obtenerPivots(String symbol, int atrLength, float slipRatioPct, int longitudVelas,
            int aniosHistorico, int numeroPivotes);
}

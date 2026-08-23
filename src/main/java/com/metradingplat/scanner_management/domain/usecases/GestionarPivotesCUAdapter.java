package com.metradingplat.scanner_management.domain.usecases;

import com.metradingplat.scanner_management.application.input.GestionarPivotesCUIntPort;
import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.domain.models.PivotesEncontrados;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GestionarPivotesCUAdapter implements GestionarPivotesCUIntPort {

    private final FuenteMensajesSignalProcessingIntPort objFuenteMensajesSignalProcessing;

    @Override
    public PivotesEncontrados obtenerPivots(String symbol, int atrLength, float slipRatioPct, int longitudVelas,
            int aniosHistorico, int numeroPivotes) {
        return this.objFuenteMensajesSignalProcessing.obtenerPivots(
                symbol, atrLength, slipRatioPct, longitudVelas, aniosHistorico, numeroPivotes);
    }
}

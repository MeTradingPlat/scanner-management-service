package com.metradingplat.scanner_management.application.input;

import com.metradingplat.scanner_management.domain.models.PivotesEncontrados;

public interface GestionarPivotesCUIntPort {
    PivotesEncontrados obtenerPivots(String symbol, int atrLength, float slipRatioPct, int longitudVelas,
            int aniosHistorico, int numeroPivotes);
}

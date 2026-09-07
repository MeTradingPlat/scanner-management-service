package com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna;

import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.BaseException;

// El detalle real de signal-processing-service (motivo de debug, ej. "El
// mercado todavia no abrio hoy") queda solo en el log.error de
// ComunicacionSignalProcessingAdapter -- el mensaje de esta excepcion es una
// llave i18n generica, no ese texto crudo (el usuario final no tiene por que
// entender la respuesta interna de otro microservicio, ver RestApiExceptionHandler).
public class PivotesNoDisponiblesException extends BaseException {
    public PivotesNoDisponiblesException() {
        super(CodigoError.PIVOTES_NO_DISPONIBLES, "error.pivots.notAvailable");
    }
}

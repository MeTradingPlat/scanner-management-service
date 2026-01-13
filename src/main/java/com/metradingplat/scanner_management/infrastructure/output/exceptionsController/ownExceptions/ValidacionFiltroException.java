package com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions;

import java.util.List;

import lombok.Getter;

import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;

@Getter
public class ValidacionFiltroException extends BaseException {
    private final List<ResultadoValidacion> erroresValidacion;

    public ValidacionFiltroException(List<ResultadoValidacion> erroresValidacion) {
        super(CodigoError.VIOLACION_REGLA_DE_NEGOCIO, "");
        this.erroresValidacion = erroresValidacion;
    }
}
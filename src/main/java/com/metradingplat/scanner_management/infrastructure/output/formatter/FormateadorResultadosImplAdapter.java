package com.metradingplat.scanner_management.infrastructure.output.formatter;

import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.EntidadNoExisteException;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.EntidadYaExisteException;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.EstadoDenegadoException;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.ReglaNegocioException;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.ValidacionFiltroException;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FormateadorResultadosImplAdapter implements FormateadorResultadosIntPort {

    @Override
    public void errorEntidadYaExiste(String llaveMensaje, Object... args) {
        EntidadYaExisteException objException = new EntidadYaExisteException(llaveMensaje, args);
        throw objException;
    }

    @Override
    public void errorEntidadNoExiste(String llaveMensaje, Object... args) {
        EntidadNoExisteException objException = new EntidadNoExisteException(llaveMensaje, args);
        throw objException;
    }

    @Override
    public void errorEstadoDenegado(String llaveMensaje, Object... args) {
        EstadoDenegadoException objException = new EstadoDenegadoException(llaveMensaje, args);
        throw objException;
    }

    @Override
    public void errorReglaNegocioViolada(String llaveMensaje, Object... args) {
        ReglaNegocioException objException = new ReglaNegocioException(llaveMensaje, args);
        throw objException;
    }

    @Override
    public void errorValidacionFiltro(List<ResultadoValidacion> errorValidaciones) {
        ValidacionFiltroException objException = new ValidacionFiltroException(errorValidaciones);
        throw objException;
    }
}
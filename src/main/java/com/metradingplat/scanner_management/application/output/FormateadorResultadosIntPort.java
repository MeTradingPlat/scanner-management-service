package com.metradingplat.scanner_management.application.output;

import java.util.List;

import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;

public interface FormateadorResultadosIntPort {
    public void errorEntidadYaExiste(String llaveMensaje, Object... args);

    public void errorEntidadNoExiste(String llaveMensaje, Object... args);

    public void errorEstadoDenegado(String llaveMensaje, Object... args);

    public void errorReglaNegocioViolada(String llaveMensaje, Object... args);

    public void errorValidacionFiltro(List<ResultadoValidacion> errorValidaciones);
}
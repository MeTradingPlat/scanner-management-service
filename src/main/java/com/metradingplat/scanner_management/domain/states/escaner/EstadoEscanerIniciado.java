package com.metradingplat.scanner_management.domain.states.escaner;

import com.metradingplat.scanner_management.domain.states.GestorEstadoEscaner;

public class EstadoEscanerIniciado implements IEstadoEscaner {

    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "scanner.state.alreadyStarted");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerDetenido objEstado = new EstadoEscanerDetenido();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "scanner.state.stopped.success");
    }

    @Override
    public ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerArchivado objEstado = new EstadoEscanerArchivado();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "scanner.state.archived.success");
    }

    @Override
    public ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "error.stateChange.notAllowed");
    }

    @Override
    public String toString() {
        return "scanner.state.started.success";
    }

}

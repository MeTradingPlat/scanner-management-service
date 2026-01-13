package com.metradingplat.scanner_management.domain.states.escaner;

import com.metradingplat.scanner_management.domain.states.GestorEstadoEscaner;

public class EstadoEscanerDetenido implements IEstadoEscaner {
    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerIniciado objEstado = new EstadoEscanerIniciado();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "scanner.state.started.success");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "scanner.state.alreadyStopped");
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
        return "scanner.state.stopped.success";
    }
}

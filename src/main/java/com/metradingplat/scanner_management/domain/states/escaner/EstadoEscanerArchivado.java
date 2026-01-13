package com.metradingplat.scanner_management.domain.states.escaner;

import com.metradingplat.scanner_management.domain.states.GestorEstadoEscaner;

public class EstadoEscanerArchivado implements IEstadoEscaner {

    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "scanner.state.archived.start.denied");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "scanner.state.archived.stop.denied");
    }

    @Override
    public ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "scanner.state.archived.archive.denied");
    }

    @Override
    public ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerDetenido objEstado = new EstadoEscanerDetenido();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "scanner.state.unarchived.success");
    }

    @Override
    public String toString() {
        return "scanner.state.archived.success";
    }
}

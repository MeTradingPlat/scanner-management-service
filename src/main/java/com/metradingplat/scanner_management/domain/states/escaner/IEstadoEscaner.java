package com.metradingplat.scanner_management.domain.states.escaner;

import com.metradingplat.scanner_management.domain.states.GestorEstadoEscaner;

public interface IEstadoEscaner {
    ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado);

    ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado);

    ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado);

    ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado);
}
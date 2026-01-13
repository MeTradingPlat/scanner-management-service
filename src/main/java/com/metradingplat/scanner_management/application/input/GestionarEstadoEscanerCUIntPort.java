package com.metradingplat.scanner_management.application.input;

import com.metradingplat.scanner_management.domain.models.EstadoEscaner;

public interface GestionarEstadoEscanerCUIntPort {
    public EstadoEscaner iniciarEscaner(Long id);

    public EstadoEscaner detenerEscaner(Long id);

    public EstadoEscaner archivarEscaner(Long id);

    public EstadoEscaner desarchivarEscaner(Long id);
}

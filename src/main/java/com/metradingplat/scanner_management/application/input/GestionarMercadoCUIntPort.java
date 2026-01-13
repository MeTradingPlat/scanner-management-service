package com.metradingplat.scanner_management.application.input;

import java.util.List;

import com.metradingplat.scanner_management.domain.models.Mercado;

public interface GestionarMercadoCUIntPort {
    public List<Mercado> listarEntidadesMercado();
}
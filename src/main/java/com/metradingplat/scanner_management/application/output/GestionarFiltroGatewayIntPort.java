package com.metradingplat.scanner_management.application.output;

import java.util.List;

import com.metradingplat.scanner_management.domain.models.Filtro;

public interface GestionarFiltroGatewayIntPort {
    public List<Filtro> obtenerFiltrosGuardados(Long idEscaner);

    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros);
}

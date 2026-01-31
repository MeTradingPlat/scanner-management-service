package com.metradingplat.scanner_management.application.input;

import com.metradingplat.scanner_management.domain.models.Escaner;

import java.util.List;

public interface GestionarEscanerCUIntPort {
    public Escaner crearEscaner(Escaner objEscaner);

    public Escaner obtenerEscanerPorId(Long idEscaner);

    public List<Escaner> listarEscaneres();

    public List<Escaner> listarEscaneresArchivados();

    public Escaner actualizarEscaner(Escaner escaner);

    public Boolean eliminarEscaner(Long idEscaner);

    public List<Escaner> listarEscaneresIniciados();
}
package com.metradingplat.scanner_management.application.output;

/**
 * Puerto de salida para limpiar datos relacionados a un escaner
 * en otros servicios (logs) cuando se elimina.
 */
public interface LimpiezaDatosEscanerIntPort {

    void eliminarLogsPorEscaner(Long idEscaner);
}

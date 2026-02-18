package com.metradingplat.scanner_management.application.output;

/**
 * Puerto de salida para limpiar datos relacionados a un escaner
 * en otros servicios (logs, activos) cuando se elimina.
 */
public interface LimpiezaDatosEscanerIntPort {

    void eliminarLogsPorEscaner(Long idEscaner);

    void eliminarActivosPorEscaner(Long idEscaner);
}

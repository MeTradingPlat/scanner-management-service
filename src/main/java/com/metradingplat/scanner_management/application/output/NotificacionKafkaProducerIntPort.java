package com.metradingplat.scanner_management.application.output;

public interface NotificacionKafkaProducerIntPort {
    void publicarNotificacion(Long idEscaner, String tipo, String nivel, String mensaje, String categoria);

    void publicarCambioEstadoEscaner(Long idEscaner, String nombreEscaner, String estadoAnterior, String estadoNuevo, String razon);
}

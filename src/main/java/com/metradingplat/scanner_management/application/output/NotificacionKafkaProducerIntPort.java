package com.metradingplat.scanner_management.application.output;

public interface NotificacionKafkaProducerIntPort {
    void publicarNotificacion(Long idEscaner, String tipo, String nivel, String mensaje, String categoria);
}

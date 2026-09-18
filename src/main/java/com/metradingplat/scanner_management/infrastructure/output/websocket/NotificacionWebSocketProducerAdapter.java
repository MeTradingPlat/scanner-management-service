package com.metradingplat.scanner_management.infrastructure.output.websocket;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metradingplat.scanner_management.application.output.NotificacionKafkaProducerIntPort;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Reemplaza al KafkaTemplate.send(TOPIC_LOGS/TOPIC_SCANNER_STATE, ...) --
 * mantiene 2 conexiones WS salientes persistentes (log-service y
 * notification-service), con reconexion automatica (ver
 * OutboundEventWebSocketClient).
 */
@Component
@Slf4j
public class NotificacionWebSocketProducerAdapter implements NotificacionKafkaProducerIntPort {

    private final OutboundEventWebSocketClient logServiceClient;
    private final OutboundEventWebSocketClient notificationServiceClient;
    private final ObjectMapper objectMapper;

    public NotificacionWebSocketProducerAdapter(
            @Value("${service.log-service.ws-url}") String logServiceWsUrl,
            @Value("${service.notification-service.ws-url}") String notificationServiceWsUrl,
            ObjectMapper objectMapper) {
        this.logServiceClient = new OutboundEventWebSocketClient(logServiceWsUrl, "log-service");
        this.notificationServiceClient = new OutboundEventWebSocketClient(notificationServiceWsUrl,
                "notification-service");
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public void publicarNotificacion(Long idEscaner, String tipo, String nivel, String mensaje, String categoria,
            String metadatos) {
        log.info("[WS-PRODUCER] Preparando notificacion: idEscaner={}, tipo={}, nivel={}, categoria={}",
                idEscaner, tipo, nivel, categoria);
        Map<String, Object> notificacion = new HashMap<>();
        notificacion.put("servicioOrigen", "scanner-management-service");
        notificacion.put("nivel", nivel);
        notificacion.put("mensaje", mensaje);
        notificacion.put("idEscaner", idEscaner);
        notificacion.put("categoria", categoria);
        notificacion.put("tipo", tipo);
        // "evento" en metadatos es un codigo neutral (ej. SCANNER_INICIADO) para
        // que el frontend arme el texto traducido -- "mensaje" se deja en
        // espanol solo como respaldo de compatibilidad para filas viejas sin
        // metadatos y para quien mire los logs crudos del backend.
        if (metadatos != null) {
            notificacion.put("metadatos", metadatos);
        }
        notificacion.put("timestamp", LocalDateTime.now().toString());

        this.logServiceClient.send(this.objectMapper.writeValueAsString(notificacion));
        log.info("[WS-PRODUCER] Notificacion enviada a log-service: idEscaner={}, mensaje={}", idEscaner, mensaje);
    }

    @Override
    @SneakyThrows
    public void publicarCambioEstadoEscaner(Long idEscaner, String nombreEscaner, String estadoAnterior,
            String estadoNuevo, String razon) {
        log.info("[WS-PRODUCER] Publicando cambio de estado: idEscaner={}, {} -> {}, razon={}",
                idEscaner, estadoAnterior, estadoNuevo, razon);
        Map<String, Object> evento = new HashMap<>();
        evento.put("idEscaner", idEscaner);
        evento.put("nombreEscaner", nombreEscaner);
        evento.put("estadoAnterior", estadoAnterior);
        evento.put("estadoNuevo", estadoNuevo);
        evento.put("razon", razon);
        evento.put("timestamp", LocalDateTime.now().toString());
        evento.put("servicioOrigen", "scanner-management-service");

        this.notificationServiceClient.send(this.objectMapper.writeValueAsString(evento));
        log.info("[WS-PRODUCER] Cambio de estado enviado a notification-service: idEscaner={}, {} -> {}",
                idEscaner, estadoAnterior, estadoNuevo);
    }
}

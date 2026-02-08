package com.metradingplat.scanner_management.infrastructure.output.kafka;

import com.metradingplat.scanner_management.application.output.NotificacionKafkaProducerIntPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacionKafkaProducerAdapter implements NotificacionKafkaProducerIntPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_LOGS = "logs.notifications";
    private static final String TOPIC_SCANNER_STATE = "scanner.state";

    @Override
    public void publicarNotificacion(Long idEscaner, String tipo, String nivel, String mensaje, String categoria) {
        log.info("[KAFKA-PRODUCER] Preparando notificacion: idEscaner={}, tipo={}, nivel={}, categoria={}",
                idEscaner, tipo, nivel, categoria);
        Map<String, Object> notificacion = new HashMap<>();
        notificacion.put("servicioOrigen", "scanner-management-service");
        notificacion.put("nivel", nivel);
        notificacion.put("mensaje", mensaje);
        notificacion.put("idEscaner", idEscaner);
        notificacion.put("categoria", categoria);
        notificacion.put("tipo", tipo);
        notificacion.put("timestamp", LocalDateTime.now().toString());

        try {
            kafkaTemplate.send(TOPIC_LOGS, notificacion);
            log.info("[KAFKA-PRODUCER] Notificacion enviada OK a topic={}: idEscaner={}, mensaje={}",
                    TOPIC_LOGS, idEscaner, mensaje);
        } catch (Exception e) {
            log.error("[KAFKA-PRODUCER] ERROR enviando notificacion: idEscaner={}, error={}",
                    idEscaner, e.getMessage());
        }
    }

    @Override
    public void publicarCambioEstadoEscaner(Long idEscaner, String nombreEscaner, String estadoAnterior, String estadoNuevo, String razon) {
        log.info("[KAFKA-PRODUCER] Publicando cambio de estado: idEscaner={}, {} -> {}, razon={}",
                idEscaner, estadoAnterior, estadoNuevo, razon);
        Map<String, Object> evento = new HashMap<>();
        evento.put("idEscaner", idEscaner);
        evento.put("nombreEscaner", nombreEscaner);
        evento.put("estadoAnterior", estadoAnterior);
        evento.put("estadoNuevo", estadoNuevo);
        evento.put("razon", razon);
        evento.put("timestamp", LocalDateTime.now().toString());
        evento.put("servicioOrigen", "scanner-management-service");

        try {
            kafkaTemplate.send(TOPIC_SCANNER_STATE, evento);
            log.info("[KAFKA-PRODUCER] Cambio de estado enviado OK a topic={}: idEscaner={}, {} -> {}",
                    TOPIC_SCANNER_STATE, idEscaner, estadoAnterior, estadoNuevo);
        } catch (Exception e) {
            log.error("[KAFKA-PRODUCER] ERROR enviando cambio de estado: idEscaner={}, error={}",
                    idEscaner, e.getMessage());
        }
    }
}

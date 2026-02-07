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
    private static final String TOPIC = "logs.notifications";

    @Override
    public void publicarNotificacion(Long idEscaner, String tipo, String nivel, String mensaje, String categoria) {
        Map<String, Object> notificacion = new HashMap<>();
        notificacion.put("servicioOrigen", "scanner-management-service");
        notificacion.put("nivel", nivel);
        notificacion.put("mensaje", mensaje);
        notificacion.put("idEscaner", idEscaner);
        notificacion.put("categoria", categoria);
        notificacion.put("timestamp", LocalDateTime.now().toString());

        kafkaTemplate.send(TOPIC, notificacion);
        log.debug("Notificacion publicada: escaner={}, mensaje={}", idEscaner, mensaje);
    }
}

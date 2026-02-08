package com.metradingplat.scanner_management.infrastructure.input.kafkaGestionarEstado.listener;

import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.infrastructure.input.kafkaGestionarEstado.dto.EstadoEscanerEventoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EstadoEscanerKafkaListener {

    private final GestionarEstadoEscanerCUIntPort objGestionarEstadoEscanerCU;

    @KafkaListener(topics = "scanner.state", groupId = "scanner-management-group")
    public void procesarCambioEstado(EstadoEscanerEventoDTO evento) {
        log.info("Recibido evento de estado: escaner={}, estado={}, razon={}, origen={}",
                evento.getIdEscaner(), evento.getEstadoNuevo(), evento.getRazon(), evento.getServicioOrigen());

        // Ignorar eventos publicados por este mismo servicio (evita loop)
        if ("scanner-management-service".equals(evento.getServicioOrigen())) {
            log.debug("Ignorando evento propio de scanner-management-service");
            return;
        }

        try {
            if ("DETENIDO".equals(evento.getEstadoNuevo()) &&
                evento.getRazon() != null && evento.getRazon().startsWith("ESCANER_UNA_VEZ")) {
                // Detener escaner internamente (sin notificar a signal-processing, ya lo sabe)
                objGestionarEstadoEscanerCU.detenerEscanerInterno(evento.getIdEscaner());
                log.info("Escaner {} detenido exitosamente via evento Kafka", evento.getIdEscaner());
            }
        } catch (Exception e) {
            log.error("Error procesando evento de estado para escaner {}: {}",
                evento.getIdEscaner(), e.getMessage());
        }
    }
}

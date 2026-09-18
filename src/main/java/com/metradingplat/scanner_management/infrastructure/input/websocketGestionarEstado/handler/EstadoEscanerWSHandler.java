package com.metradingplat.scanner_management.infrastructure.input.websocketGestionarEstado.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.infrastructure.input.kafkaGestionarEstado.dto.EstadoEscanerEventoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Reemplaza al @KafkaListener de topic "scanner.state" -- signal-processing-service
 * se conecta aca como cliente WS para avisar cuando detiene internamente un
 * escaner (ej. ejecucion UNA_VEZ ya terminada), asi este servicio sincroniza
 * su propio estado sin que el usuario tenga que hacerlo a mano.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EstadoEscanerWSHandler extends TextWebSocketHandler {

    private final GestionarEstadoEscanerCUIntPort objGestionarEstadoEscanerCU;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        EstadoEscanerEventoDTO evento = this.objectMapper.readValue(message.getPayload(),
                EstadoEscanerEventoDTO.class);
        log.info("Recibido evento de estado via WS: escaner={}, estado={}, razon={}, origen={}",
                evento.getIdEscaner(), evento.getEstadoNuevo(), evento.getRazon(), evento.getServicioOrigen());

        try {
            if ("DETENIDO".equals(evento.getEstadoNuevo()) &&
                    evento.getRazon() != null && evento.getRazon().startsWith("ESCANER_UNA_VEZ")) {
                this.objGestionarEstadoEscanerCU.detenerEscanerInterno(evento.getIdEscaner());
                log.info("Escaner {} detenido exitosamente via evento WS", evento.getIdEscaner());
            }
        } catch (Exception e) {
            log.error("Error procesando evento de estado para escaner {}: {}",
                    evento.getIdEscaner(), e.getMessage());
        }
    }
}

package com.metradingplat.scanner_management.infrastructure.input.websocketGestionarEstado.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.metradingplat.scanner_management.infrastructure.input.websocketGestionarEstado.handler.EstadoEscanerWSHandler;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint interno (servicio a servicio, sin pasar por el Gateway) que
 * reemplaza al @KafkaListener de topic "scanner.state" de este servicio.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class InternalWebSocketConfig implements WebSocketConfigurer {

    private final EstadoEscanerWSHandler estadoEscanerWSHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.estadoEscanerWSHandler, "/ws/internal/estado-escaner");
    }
}

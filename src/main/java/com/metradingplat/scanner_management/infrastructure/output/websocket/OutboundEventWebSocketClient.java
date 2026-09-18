package com.metradingplat.scanner_management.infrastructure.output.websocket;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * Conexion WS saliente persistente, con reconexion automatica -- reemplaza
 * un KafkaTemplate.send(topic, ...) puntual. Un envio con la conexion caida
 * simplemente se descarta (mismo best-effort que Kafka con retries=3 y
 * despues loguear el error, no una cola de reintento persistente).
 */
@Slf4j
public class OutboundEventWebSocketClient {

    private static final int RECONNECT_DELAY_SECONDS = 5;

    private final String url;
    private final String etiqueta;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ScheduledExecutorService scheduler;
    private volatile WebSocket webSocket;
    private volatile boolean shuttingDown = false;

    public OutboundEventWebSocketClient(String url, String etiqueta) {
        this.url = url;
        this.etiqueta = etiqueta;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ws-out-" + etiqueta);
            t.setDaemon(true);
            return t;
        });
        this.connect();
    }

    private void connect() {
        this.httpClient.newWebSocketBuilder()
                // El Gateway exige este header en todas las rutas internas
                // (GatewayHeaderFilter) -- sin el, el handshake devuelve 403
                // antes de abrir el socket.
                .header("X-Gateway-Passed", "true")
                .buildAsync(URI.create(this.url), new ReconnectingListener())
                .whenComplete((ws, error) -> {
                    if (error != null) {
                        log.warn("No se pudo conectar a {} via WS ({}): {}", this.etiqueta, this.url,
                                error.getMessage());
                        this.scheduleReconnect();
                        return;
                    }
                    this.webSocket = ws;
                    log.info("Conectado a {} via WS: {}", this.etiqueta, this.url);
                });
    }

    private void scheduleReconnect() {
        if (this.shuttingDown) {
            return;
        }
        this.scheduler.schedule(this::connect, RECONNECT_DELAY_SECONDS, TimeUnit.SECONDS);
    }

    public void send(String json) {
        WebSocket ws = this.webSocket;
        if (ws == null || ws.isOutputClosed()) {
            log.warn("WS a {} no disponible, se descarta el envio", this.etiqueta);
            return;
        }
        try {
            ws.sendText(json, true);
        } catch (Exception e) {
            log.error("Fallo enviando por WS a {}: {}", this.etiqueta, e.getMessage());
        }
    }

    @PreDestroy
    void shutdown() {
        this.shuttingDown = true;
        this.scheduler.shutdownNow();
        if (this.webSocket != null) {
            this.webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "shutdown");
        }
    }

    private class ReconnectingListener implements WebSocket.Listener {
        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            log.warn("WS a {} cerrado (status={}, reason={}), reintentando en {}s",
                    OutboundEventWebSocketClient.this.etiqueta, statusCode, reason, RECONNECT_DELAY_SECONDS);
            OutboundEventWebSocketClient.this.webSocket = null;
            OutboundEventWebSocketClient.this.scheduleReconnect();
            return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            log.warn("Error en WS a {}: {}, reintentando en {}s",
                    OutboundEventWebSocketClient.this.etiqueta, error.getMessage(), RECONNECT_DELAY_SECONDS);
            OutboundEventWebSocketClient.this.webSocket = null;
            OutboundEventWebSocketClient.this.scheduleReconnect();
        }
    }
}

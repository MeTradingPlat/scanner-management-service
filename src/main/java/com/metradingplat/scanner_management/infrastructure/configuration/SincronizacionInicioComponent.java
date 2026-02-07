package com.metradingplat.scanner_management.infrastructure.configuration;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.domain.models.Escaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Componente que sincroniza los escaneres iniciados con signal-processing
 * cuando la aplicacion inicia.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SincronizacionInicioComponent {

    private final GestionarEscanerGatewayIntPort objGestionarEscanerGateway;
    private final FuenteMensajesSignalProcessingIntPort objFuenteMensajesSignalProcessing;

    @EventListener(ApplicationReadyEvent.class)
    public void sincronizarEscaneresAlIniciar() {
        log.info("Iniciando sincronizacion de escaneres con signal-processing...");

        try {
            List<Escaner> escaneresIniciados = objGestionarEscanerGateway.obtenerEscaneresIniciados();

            if (escaneresIniciados.isEmpty()) {
                log.info("No hay escaneres iniciados para sincronizar.");
                return;
            }

            log.info("Se encontraron {} escaneres iniciados. Notificando a signal-processing...",
                    escaneresIniciados.size());

            int exitosos = 0;
            int fallidos = 0;

            for (Escaner escanerBasico : escaneresIniciados) {
                try {
                    // Obtener escaner completo con filtros para enviarlo a signal-processing
                    Escaner escanerCompleto = objGestionarEscanerGateway.obtenerEscanerPorId(
                            escanerBasico.getIdEscaner());
                    objFuenteMensajesSignalProcessing.notificarEscanerIniciado(escanerCompleto);
                    exitosos++;
                    log.debug("Escaner sincronizado: id={}, nombre={}",
                            escanerCompleto.getIdEscaner(), escanerCompleto.getNombre());
                } catch (Exception e) {
                    fallidos++;
                    log.warn("Error sincronizando escaner id={}: {}",
                            escanerBasico.getIdEscaner(), e.getMessage());
                }
            }

            log.info("Sincronizacion completada. Exitosos: {}, Fallidos: {}", exitosos, fallidos);

        } catch (Exception e) {
            log.error("Error durante la sincronizacion de escaneres: {}", e.getMessage());
        }
    }
}

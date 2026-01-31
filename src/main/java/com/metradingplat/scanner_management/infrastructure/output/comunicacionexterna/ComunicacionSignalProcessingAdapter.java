package com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.domain.models.Escaner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class ComunicacionSignalProcessingAdapter implements FuenteMensajesSignalProcessingIntPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${service.signal-processing.url:http://localhost:8000}")
    private String signalProcessingUrl;

    @Override
    public void notificarEscanerIniciado(Escaner escaner) {
        try {
            restTemplate.postForLocation(signalProcessingUrl + "/escaner", escaner);
        } catch (Exception e) {
            // Log error safely
            System.err.println("Error notificando inicio escaner a signal-processing: " + e.getMessage());
        }
    }

    @Override
    public void notificarEscanerDetenido(Long idEscaner) {
        try {
            restTemplate.postForLocation(signalProcessingUrl + "/escaner/" + idEscaner + "/detener", null);
        } catch (Exception e) {
            System.err.println("Error notificando detencion escaner a signal-processing: " + e.getMessage());
        }
    }
}

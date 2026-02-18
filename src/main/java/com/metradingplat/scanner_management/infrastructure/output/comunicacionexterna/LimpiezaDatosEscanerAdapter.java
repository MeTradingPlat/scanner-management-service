package com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna;

import com.metradingplat.scanner_management.application.output.LimpiezaDatosEscanerIntPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class LimpiezaDatosEscanerAdapter implements LimpiezaDatosEscanerIntPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${service.log.url:http://log-service:8084}")
    private String logServiceUrl;

    @Value("${service.asset-management.url:http://asset-management-service:8083}")
    private String assetManagementServiceUrl;

    @Override
    public void eliminarLogsPorEscaner(Long idEscaner) {
        String url = logServiceUrl + "/api/logs/escaner/" + idEscaner;
        log.info("Eliminando logs del escaner {}: {}", idEscaner, url);
        try {
            restTemplate.delete(url);
            log.info("Logs del escaner {} eliminados correctamente", idEscaner);
        } catch (Exception e) {
            log.error("Error eliminando logs del escaner {}: {}", idEscaner, e.getMessage());
        }
    }

    @Override
    public void eliminarActivosPorEscaner(Long idEscaner) {
        String url = assetManagementServiceUrl + "/api/activos/escaner/" + idEscaner;
        log.info("Eliminando activos del escaner {}: {}", idEscaner, url);
        try {
            restTemplate.delete(url);
            log.info("Activos del escaner {} eliminados correctamente", idEscaner);
        } catch (Exception e) {
            log.error("Error eliminando activos del escaner {}: {}", idEscaner, e.getMessage());
        }
    }
}

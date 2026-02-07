package com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.Filtro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
@Slf4j
public class ComunicacionSignalProcessingAdapter implements FuenteMensajesSignalProcessingIntPort {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GestorEstrategiaFiltroIntPort gestorFiltros;

    @Value("${service.signal-processing.url:http://localhost:8000}")
    private String signalProcessingUrl;

    public ComunicacionSignalProcessingAdapter(GestorEstrategiaFiltroIntPort gestorFiltros) {
        this.gestorFiltros = gestorFiltros;
    }

    @Override
    public void notificarEscanerIniciado(Escaner escaner) {
        String url = signalProcessingUrl + "/api/signal-processing/escaner";
        log.info("[SIGNAL-PROC-ADAPTER] Notificando escaner iniciado: id={}, nombre={}, url={}",
                escaner.getIdEscaner(), escaner.getNombre(), url);
        try {
            enriquecerFiltrosConCategoria(escaner);
            restTemplate.postForLocation(url, escaner);
            log.info("[SIGNAL-PROC-ADAPTER] Notificacion escaner iniciado OK: id={}", escaner.getIdEscaner());
        } catch (Exception e) {
            log.error("[SIGNAL-PROC-ADAPTER] ERROR notificando inicio escaner id={}: {}",
                    escaner.getIdEscaner(), e.getMessage());
        }
    }

    private void enriquecerFiltrosConCategoria(Escaner escaner) {
        if (escaner.getFiltros() == null) {
            return;
        }
        for (Filtro filtro : escaner.getFiltros()) {
            if (filtro.getObjCategoria() == null && filtro.getEnumFiltro() != null) {
                Filtro filtroInfo = gestorFiltros.obtenerInfomracionFiltro(filtro.getEnumFiltro());
                CategoriaFiltro categoria = filtroInfo.getObjCategoria();
                filtro.setObjCategoria(categoria);
                filtro.setEtiquetaNombre(filtroInfo.getEtiquetaNombre());
                filtro.setEtiquetaDescripcion(filtroInfo.getEtiquetaDescripcion());
                log.debug("[SIGNAL-PROC-ADAPTER] Filtro enriquecido: enum={}, categoria={}",
                        filtro.getEnumFiltro(), categoria != null ? categoria.getEnumCategoriaFiltro() : "null");
            }
        }
    }

    @Override
    public void notificarEscanerDetenido(Long idEscaner) {
        String url = signalProcessingUrl + "/api/signal-processing/escaner/" + idEscaner + "/detener";
        log.info("[SIGNAL-PROC-ADAPTER] Notificando escaner detenido: id={}, url={}", idEscaner, url);
        try {
            restTemplate.postForLocation(url, null);
            log.info("[SIGNAL-PROC-ADAPTER] Notificacion escaner detenido OK: id={}", idEscaner);
        } catch (Exception e) {
            log.error("[SIGNAL-PROC-ADAPTER] ERROR notificando detencion escaner id={}: {}",
                    idEscaner, e.getMessage());
        }
    }
}

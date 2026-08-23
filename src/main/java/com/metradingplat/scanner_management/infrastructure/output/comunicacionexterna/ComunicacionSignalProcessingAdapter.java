package com.metradingplat.scanner_management.infrastructure.output.comunicacionexterna;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoCalendario;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.PivotLevel;
import com.metradingplat.scanner_management.domain.models.PivotesEncontrados;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ComunicacionSignalProcessingAdapter implements FuenteMensajesSignalProcessingIntPort {

    private final RestTemplate restTemplate;
    private final GestorEstrategiaFiltroIntPort gestorFiltros;

    @Value("${service.signal-processing.url:http://localhost:8000}")
    private String signalProcessingUrl;

    public ComunicacionSignalProcessingAdapter(RestTemplate restTemplate, GestorEstrategiaFiltroIntPort gestorFiltros) {
        this.restTemplate = restTemplate;
        this.gestorFiltros = gestorFiltros;
    }

    @Override
    public void notificarEscanerIniciado(Escaner escaner) {
        String url = signalProcessingUrl + "/signal-processing/escaner";
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
        String url = signalProcessingUrl + "/signal-processing/escaner/" + idEscaner + "/detener";
        log.info("[SIGNAL-PROC-ADAPTER] Notificando escaner detenido: id={}, url={}", idEscaner, url);
        try {
            restTemplate.postForLocation(url, null);
            log.info("[SIGNAL-PROC-ADAPTER] Notificacion escaner detenido OK: id={}", idEscaner);
        } catch (Exception e) {
            log.error("[SIGNAL-PROC-ADAPTER] ERROR notificando detencion escaner id={}: {}",
                    idEscaner, e.getMessage());
        }
    }

    // signal-processing-service es la unica fuente del calendario de dias
    // habiles (feriados de mercado incluidos) -- no se duplica esa lista aca,
    // solo se consulta.
    @Override
    public EstadoCalendario obtenerEstadoCalendario() {
        String url = signalProcessingUrl + "/signal-processing/calendar/estado";
        try {
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);
            boolean hoyEsDiaHabil = Boolean.TRUE.equals(respuesta.get("hoyEsDiaHabil"));
            LocalDate proximoDiaHabil = LocalDate.parse((String) respuesta.get("proximoDiaHabil"));
            return new EstadoCalendario(hoyEsDiaHabil, proximoDiaHabil);
        } catch (Exception e) {
            log.error("[SIGNAL-PROC-ADAPTER] ERROR consultando calendario: {}", e.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PivotesEncontrados obtenerPivots(String symbol, int atrLength, float slipRatioPct, int longitudVelas,
            int aniosHistorico, int numeroPivotes) {
        String url = UriComponentsBuilder.fromHttpUrl(signalProcessingUrl + "/signal-processing/pivots/" + symbol)
                .queryParam("atr_length", atrLength)
                .queryParam("slip_ratio_pct", slipRatioPct)
                .queryParam("longitud_velas", longitudVelas)
                .queryParam("anios_historico", aniosHistorico)
                .queryParam("numero_pivotes", numeroPivotes)
                .toUriString();
        try {
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);
            PivotesEncontrados pivotes = new PivotesEncontrados();
            pivotes.setSymbol((String) respuesta.get("symbol"));
            pivotes.setCurrentPrice(new BigDecimal(respuesta.get("currentPrice").toString()));
            pivotes.setTimeframe((String) respuesta.get("timeframe"));
            pivotes.setResistances(mapearNivelesPivote((List<Map<String, Object>>) respuesta.get("resistances")));
            pivotes.setSupports(mapearNivelesPivote((List<Map<String, Object>>) respuesta.get("supports")));
            return pivotes;
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            // Captura aparte de HttpStatusCodeException para loguear el body real
            // (ej. el detail de la HTTPException de FastAPI) -- antes solo se veia
            // "404 Not Found" o "500 Internal Server Error" sin ninguna pista de
            // la causa real, indistinguible de "sin historial suficiente" en logs.
            log.error("[SIGNAL-PROC-ADAPTER] ERROR consultando pivots de {}: status={} body={}",
                    symbol, e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("[SIGNAL-PROC-ADAPTER] ERROR consultando pivots de {}: {}", symbol, e.getMessage());
            return null;
        }
    }

    private List<PivotLevel> mapearNivelesPivote(List<Map<String, Object>> niveles) {
        if (niveles == null) {
            return List.of();
        }
        return niveles.stream()
                .map(nivel -> new PivotLevel(
                        LocalDateTime.parse(((String) nivel.get("timestamp")).replace("Z", "")),
                        new BigDecimal(nivel.get("price").toString())))
                .collect(Collectors.toList());
    }
}

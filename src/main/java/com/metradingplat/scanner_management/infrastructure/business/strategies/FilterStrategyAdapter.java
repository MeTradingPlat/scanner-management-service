package com.metradingplat.scanner_management.infrastructure.business.strategies;

import com.metradingplat.scanner_management.application.input.FilterStrategyPort;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa el puerto FilterStrategyPort.
 * Actúa como puente entre la capa de aplicación y las estrategias de filtro en
 * infraestructura.
 */
@Service
public class FilterStrategyAdapter implements FilterStrategyPort {

    private final GestorFiltroFactory gestorEstrategiaFiltro;

    public FilterStrategyAdapter(GestorFiltroFactory gestorEstrategiaFiltro) {
        this.gestorEstrategiaFiltro = gestorEstrategiaFiltro;
    }

    @Override
    public Filtro createFilter(EnumFiltro enumFiltro, Map<EnumParametro, Valor> valoresSeleccionados) {
        IFiltroFactory estrategia = gestorEstrategiaFiltro.obtenerEstrategia(enumFiltro);
        return estrategia.obtenerFiltro(valoresSeleccionados);
    }

    @Override
    public Filtro getFilterInfo(EnumFiltro enumFiltro) {
        IFiltroFactory estrategia = gestorEstrategiaFiltro.obtenerEstrategia(enumFiltro);
        return estrategia.obtenerInformacionFiltro();
    }

    @Override
    public List<Filtro> getAllFilters() {
        return gestorEstrategiaFiltro.obtenerTodasLasEstrategias()
                .values()
                .stream()
                .map(IFiltroFactory::obtenerInformacionFiltro)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> validateFilter(EnumFiltro enumFiltro, Map<EnumParametro, Valor> valoresSeleccionados) {
        IFiltroFactory estrategia = gestorEstrategiaFiltro.obtenerEstrategia(enumFiltro);
        List<ResultadoValidacion> resultados = estrategia.validarValoresSeleccionados(valoresSeleccionados);

        return resultados.stream()
                .map(r -> String.format("[%s] %s", r.enumParametro().name(), r.mensaje()))
                .collect(Collectors.toList());
    }
}

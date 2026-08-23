package com.metradingplat.scanner_management.infrastructure.business.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.metradingplat.scanner_management.application.output.GestorEstrategiaIndicadorSalidaIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacionIndicadorSalida;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.EntidadNoExisteException;

/**
 * Registry + Facade para las implementaciones de IIndicadorSalidaFactory,
 * mismo rol que GestorFiltroFactory cumple para IFiltroFactory. Sin
 * agrupacion por categoria: a diferencia de los filtros, todavia hay un solo
 * tipo de indicador de salida (PIVOTS), asi que una lista plana es
 * suficiente.
 */
@Service
public class GestorIndicadorSalidaFactory implements GestorEstrategiaIndicadorSalidaIntPort {

    private final Map<EnumIndicadorSalida, IIndicadorSalidaFactory> mapEnumIndicadorFactory;

    public GestorIndicadorSalidaFactory(Set<IIndicadorSalidaFactory> indicadores) {
        this.mapEnumIndicadorFactory = new HashMap<>();
        for (IIndicadorSalidaFactory indicador : indicadores) {
            this.mapEnumIndicadorFactory.put(indicador.obtenerEnumIndicadorSalida(), indicador);
        }
    }

    private IIndicadorSalidaFactory obtenerEstrategia(EnumIndicadorSalida tipoIndicador) {
        IIndicadorSalidaFactory estrategia = this.mapEnumIndicadorFactory.get(tipoIndicador);
        if (estrategia == null) {
            throw new EntidadNoExisteException("estrategia.indicadorSalida.no.existe", tipoIndicador.name());
        }
        return estrategia;
    }

    @Override
    public List<EnumIndicadorSalida> obtenerTodosLosIndicadores() {
        return new ArrayList<>(this.mapEnumIndicadorFactory.keySet());
    }

    @Override
    public IndicadorSalida obtenerInformacionIndicadorSalida(EnumIndicadorSalida tipoIndicador) {
        return this.obtenerEstrategia(tipoIndicador).obtenerInformacionIndicadorSalida();
    }

    @Override
    public IndicadorSalida obtenerIndicadorSalidaConValoresPorDefecto(EnumIndicadorSalida tipoIndicador) {
        return this.obtenerEstrategia(tipoIndicador).obtenerIndicadorSalida();
    }

    @Override
    public IndicadorSalida crearIndicadorSalidaConValoresSeleccionados(EnumIndicadorSalida tipoIndicador,
            Map<EnumParametroIndicadorSalida, Valor> valores) {
        return this.obtenerEstrategia(tipoIndicador).obtenerIndicadorSalida(valores);
    }

    @Override
    public Boolean validarEnumIndicadorSalida(EnumIndicadorSalida tipoIndicador) {
        return this.mapEnumIndicadorFactory.containsKey(tipoIndicador);
    }

    @Override
    public List<ResultadoValidacionIndicadorSalida> validarValoresSeleccionados(EnumIndicadorSalida tipoIndicador,
            Map<EnumParametroIndicadorSalida, Valor> valores) {
        return this.obtenerEstrategia(tipoIndicador).validarValoresSeleccionados(valores);
    }
}

package com.metradingplat.scanner_management.domain.usecases;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.metradingplat.scanner_management.application.input.GestionarIndicadorSalidaCUIntPort;
import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestorEstrategiaIndicadorSalidaIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;

@RequiredArgsConstructor
public class GestionarIndicadorSalidaCUAdapter implements GestionarIndicadorSalidaCUIntPort {

    private final GestorEstrategiaIndicadorSalidaIntPort objGestorFactoryIndicadorSalida;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;

    @Override
    public List<IndicadorSalida> obtenerIndicadoresSalida() {
        return this.objGestorFactoryIndicadorSalida.obtenerTodosLosIndicadores().stream()
                .map(this.objGestorFactoryIndicadorSalida::obtenerInformacionIndicadorSalida)
                .collect(Collectors.toList());
    }

    @Override
    public IndicadorSalida obtenerIndicadorSalidaPorDefecto(EnumIndicadorSalida enumIndicadorSalida) {
        if (!this.objGestorFactoryIndicadorSalida.validarEnumIndicadorSalida(enumIndicadorSalida)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.indicatorExit.type.notFound");
        }
        return this.objGestorFactoryIndicadorSalida.obtenerIndicadorSalidaConValoresPorDefecto(enumIndicadorSalida);
    }
}

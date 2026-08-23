package com.metradingplat.scanner_management.application.input;

import java.util.List;

import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;

public interface GestionarIndicadorSalidaCUIntPort {
    List<IndicadorSalida> obtenerIndicadoresSalida();

    IndicadorSalida obtenerIndicadorSalidaPorDefecto(EnumIndicadorSalida enumIndicadorSalida);
}

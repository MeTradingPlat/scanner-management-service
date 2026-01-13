package com.metradingplat.scanner_management.domain.usecases;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.input.GestionarMercadoCUIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;
import com.metradingplat.scanner_management.domain.models.Mercado;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class GestionarMercadoCUAdapter implements GestionarMercadoCUIntPort {

    @Override
    public List<Mercado> listarEntidadesMercado() {
        List<Mercado> mercados = Arrays.stream(EnumMercado.values())
                .map(enumMercado -> new Mercado(
                        enumMercado.getEtiqueta(),
                        enumMercado))
                .toList();
        return mercados;
    }
}
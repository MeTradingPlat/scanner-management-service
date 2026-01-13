package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumEstadoEscaner {
    ARCHIVADO,
    INICIADO,
    DETENIDO,
    DESARCHIVADO;
}
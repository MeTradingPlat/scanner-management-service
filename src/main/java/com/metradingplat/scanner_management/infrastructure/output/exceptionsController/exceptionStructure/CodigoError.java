package com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodigoError {
    ERROR_GENERICO("GC-0001", "error.generic"),
    ENTIDAD_YA_EXISTE("GC-0002", "error.entity.exists"),
    ENTIDAD_NO_ENCONTRADA("GC-0003", "error.entity.notFound"),
    CAMBIO_DE_ESTADO_NO_PERMITIDO("GC-0004", "error.stateChange.notAllowed"),
    VIOLACION_REGLA_DE_NEGOCIO("GC-0005", "error.businessRule.violation"),
    ERROR_VALIDACION("GC-0009", "error.validacion"),
    CREDENCIALES_INVALIDAS("GC-0006", "error.credentials.invalid"),
    USUARIO_DESHABILITADO("GC-0007", "error.user.disabled"),
    TOKEN_INVALIDO("GC-0008", "error.token.invalid"),
    TIPO_DE_ARGUMENTO_INVALIDO("GC-0009", "validation.type.invalid");

    private final String codigo;
    private final String llaveMensaje;
}
package com.metradingplat.scanner_management.infrastructure.input.kafkaGestionarEstado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEscanerEventoDTO {
    private Long idEscaner;
    private String nombreEscaner;
    private String estadoAnterior;
    private String estadoNuevo;
    private String razon;
    private String timestamp;
}

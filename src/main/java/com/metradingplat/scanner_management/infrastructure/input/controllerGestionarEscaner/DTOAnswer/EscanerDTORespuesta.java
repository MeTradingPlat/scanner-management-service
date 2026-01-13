package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.DTOAnswer.MercadoDTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscanerDTORespuesta {
    private Long idEscaner;
    private String nombre;
    private String descripcion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaCreacion;
    private List<MercadoDTORespuesta> mercados;
    private EstadoEscanerDTORespuesta objEstado;
    private TipoEjecucionDTORespuesta objTipoEjecucion;
}
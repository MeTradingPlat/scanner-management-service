package com.metradingplat.scanner_management.domain.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Escaner {

    private Long idEscaner;
    private String nombre;
    private String descripcion;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaCreacion;
    private EstadoEscaner objEstado;
    private TipoEjecucion objTipoEjecucion;
    private List<Mercado> mercados = new ArrayList<Mercado>();
    private List<Filtro> filtros = new ArrayList<Filtro>();
}

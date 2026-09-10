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

    /**
     * Hora de inicio del escáner en UTC.
     * IMPORTANTE: Esta hora está en timezone UTC, no local.
     * El frontend debe convertir de hora local a UTC antes de enviar,
     * y de UTC a hora local al mostrar.
     */
    private LocalTime horaInicio;

    /**
     * Hora de fin del escáner en UTC.
     * IMPORTANTE: Esta hora está en timezone UTC, no local.
     * El frontend debe convertir de hora local a UTC antes de enviar,
     * y de UTC a hora local al mostrar.
     */
    private LocalTime horaFin;

    private LocalDate fechaCreacion;
    private EstadoEscaner objEstado;
    private TipoEjecucion objTipoEjecucion;
    private List<Mercado> mercados = new ArrayList<Mercado>();
    private List<Filtro> filtros = new ArrayList<Filtro>();

    // Si es false (default), un simbolo que ya genero una señal hoy para
    // este escaner queda excluido el resto del dia. Si es true,
    // signal-processing-service permite volver a señalizarlo cuando
    // vuelve a calificar tras haber dejado de hacerlo.
    private boolean permitirMultiplesSenales;
}

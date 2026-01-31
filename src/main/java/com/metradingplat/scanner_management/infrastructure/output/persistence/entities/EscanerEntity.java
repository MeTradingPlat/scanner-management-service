package com.metradingplat.scanner_management.infrastructure.output.persistence.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "escaneres")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscanerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escaner")
    private Long idEscaner;

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @OneToOne(mappedBy = "objEscaner", cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
            CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
    private EstadoEscanerEntity objEstado;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "id_tipo_ejecucion")
    private TipoEjecucionEntity objTipoEjecucion;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "escaner_mercado", joinColumns = @JoinColumn(name = "id_escaner"), inverseJoinColumns = @JoinColumn(name = "id_mercado"))
    private List<MercadoEntity> mercados = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "objEscaner", cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
            CascadeType.MERGE }, orphanRemoval = true)
    private List<FiltroEntity> filtros = new ArrayList<>();

    public Boolean asociarConEstado() {
        Boolean resultado = false;
        if (this.objEstado != null) {
            this.objEstado.setObjEscaner(this);
            this.objEstado.setIdEscaner(this.idEscaner);
            resultado = true;
        }
        return resultado;
    }

    public Boolean asociarConTipoEjecucion() {
        Boolean resultado = false;
        if (this.objTipoEjecucion != null) {
            if (this.objTipoEjecucion.getEscaneres() == null || !this.objTipoEjecucion.getEscaneres().contains(this)) {
                this.objTipoEjecucion.getEscaneres().add(this);
            }
            resultado = true;
        }
        return resultado;
    }

    public Boolean asociarConFiltros() {
        Boolean resultado = false;
        if (this.filtros != null && !this.filtros.isEmpty()) {
            for (FiltroEntity filtro : this.filtros) {
                filtro.setObjEscaner(this);
                filtro.asociarTodo();
            }
            resultado = true;
        }
        return resultado;
    }

    public Boolean asociarConMercados() {
        Boolean resultado = false;
        if (this.mercados != null && !this.mercados.isEmpty()) {
            for (MercadoEntity mercado : this.mercados) {
                if (mercado.getEscaneres() == null || !mercado.getEscaneres().contains(this)) {
                    mercado.getEscaneres().add(this);
                }
            }
            resultado = true;
        }
        return resultado;
    }

    public void asociarTodo() {
        this.asociarConEstado();
        this.asociarConTipoEjecucion();
        this.asociarConFiltros();
        this.asociarConMercados();
    }
}
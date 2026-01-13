package com.metradingplat.scanner_management.infrastructure.output.persistence.entitys;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;

@Entity
@Table(name = "estados_escaner")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoEscanerEntity {

    @Id
    private Long idEscaner;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_estado_escaner", nullable = false)
    private EnumEstadoEscaner enumEstadoEscaner = EnumEstadoEscaner.DETENIDO;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_escaner")
    private EscanerEntity objEscaner;

    @Version
    private Long version;
}
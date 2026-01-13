package com.metradingplat.scanner_management.infrastructure.output.persistence.entitys;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoEjecucion;

@Entity
@Table(name = "tipos_ejecucion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoEjecucionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ejecucion")
    private Long idTipoEjecucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_tipo_ejecucion", nullable = false, unique = true)
    private EnumTipoEjecucion enumTipoEjecucion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "objTipoEjecucion", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE })
    private List<EscanerEntity> escaneres = new ArrayList<>();

}
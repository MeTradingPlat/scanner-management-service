package com.metradingplat.scanner_management.infrastructure.output.persistence.entitys;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumMercado;

@Entity
@Table(name = "mercados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MercadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mercado")
    private Long idMercado;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_mercado", nullable = false, unique = true)
    private EnumMercado enumMercado;

    @ManyToMany(mappedBy = "mercados", cascade = { CascadeType.MERGE })
    private List<EscanerEntity> escaneres = new ArrayList<>();
}
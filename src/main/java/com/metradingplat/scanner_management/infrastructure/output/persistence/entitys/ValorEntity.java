package com.metradingplat.scanner_management.infrastructure.output.persistence.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "valor")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ValorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor")
    private Long idValor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parametro")
    private ParametroEntity objParametro;

    @Column(name = "enum_tipo_valor")
    private String enumTipoValor;
}

package com.metradingplat.scanner_management.infrastructure.output.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "valor")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "enum_tipo_valor", discriminatorType = DiscriminatorType.STRING)
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

    // Sin columna discriminadora explicita, la asociacion polimorfica EAGER
    // objValorSeleccionado (ParametroEntity) resolvia el "case when x is not
    // null" de Hibernate pero no siempre hidrataba los campos propios de la
    // subclase (valor/valor1/valor2/enumCondicional siempre null en la
    // respuesta REST pese a estar bien guardados en la BD) -- confirmado en
    // vivo via logs SQL. El campo queda de solo lectura porque la columna ya
    // la gestiona el discriminador.
    @Column(name = "enum_tipo_valor", insertable = false, updatable = false)
    private String enumTipoValor;
}

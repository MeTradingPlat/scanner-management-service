package com.metradingplat.scanner_management.infrastructure.output.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;

@Entity
@Table(name = "parametros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParametroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro")
    private Long idParametro;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_parametro", nullable = false, length = 50)
    private EnumParametro enumParametro;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "idFiltro", nullable = false)
    private FiltroEntity objFiltro;

    // FetchMode.SELECT fuerza un SELECT propio (el mismo camino que
    // entityManager.find(), ya confirmado que hidrata bien la subclase
    // polimorfica) en vez de embeber el join en la consulta del padre -- con
    // el join embebido, Hibernate identificaba el tipo correcto pero nunca
    // poblaba los campos propios de la subclase (valor/valor1/valor2/
    // enumCondicional quedaban siempre null en la respuesta REST pese a estar
    // bien guardados), confirmado comparando ambos caminos en vivo.
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "id_valor", nullable = false, unique = true)
    @Fetch(FetchMode.SELECT)
    private ValorEntity objValorSeleccionado;

    public void asociarTodo() {
        asociarConValor();
    }

    public Boolean asociarConValor() {
        Boolean resultado = false;
        if (this.objValorSeleccionado != null) {
            this.objValorSeleccionado.setObjParametro(this);
            resultado = true;
        }
        return resultado;
    }
}

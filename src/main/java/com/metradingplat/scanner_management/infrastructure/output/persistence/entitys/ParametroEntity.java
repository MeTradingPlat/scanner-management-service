package com.metradingplat.scanner_management.infrastructure.output.persistence.entitys;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "id_valor", nullable = false, unique = true)
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

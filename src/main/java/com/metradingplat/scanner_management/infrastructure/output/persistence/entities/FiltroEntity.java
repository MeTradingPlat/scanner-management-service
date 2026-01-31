package com.metradingplat.scanner_management.infrastructure.output.persistence.entities;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;

@Entity
@Table(name = "filtros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filtro")
    private Long idFiltro;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_filtro", nullable = false, length = 50)
    private EnumFiltro enumFiltro;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "idscaner", nullable = false)
    private EscanerEntity objEscaner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "objFiltro", cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
            CascadeType.MERGE })
    private List<ParametroEntity> parametros = new ArrayList<>();

    public void asociarTodo() {
        asociarConParametros();
        for (ParametroEntity parametro : this.parametros) {
            parametro.asociarTodo();
        }
    }

    public Boolean asociarConParametros() {
        Boolean resultado = false;
        if (this.parametros != null && !this.parametros.isEmpty()) {
            for (ParametroEntity parametro : this.parametros) {
                parametro.setObjFiltro(this);
            }
            resultado = true;
        }
        return resultado;
    }
}

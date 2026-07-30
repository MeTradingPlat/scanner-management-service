package com.metradingplat.scanner_management.infrastructure.output.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "valor_string")
@PrimaryKeyJoinColumn(name = "id_valor")
@DiscriminatorValue("STRING")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValorStringEntity extends ValorEntity {

    @Column(name = "valor")
    private String valor;
}
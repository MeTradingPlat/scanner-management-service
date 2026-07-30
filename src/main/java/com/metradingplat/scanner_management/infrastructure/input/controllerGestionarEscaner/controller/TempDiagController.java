package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.controller;

import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ParametroEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorCondicionalEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempDiagController {

    @PersistenceContext
    private EntityManager entityManager;

    private String describe(ValorEntity entity) {
        if (entity == null) {
            return "null entity";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("class=").append(entity.getClass().getName());
        sb.append(" enumTipoValor=").append(entity.getEnumTipoValor());
        if (entity instanceof ValorCondicionalEntity vc) {
            sb.append(" enumCondicional=").append(vc.getEnumCondicional());
            sb.append(" isInteger=").append(vc.getIsInteger());
            sb.append(" valor1=").append(vc.getValor1());
            sb.append(" valor2=").append(vc.getValor2());
        }
        return sb.toString();
    }

    @GetMapping("/debug/valor/{id}")
    public String debugValor(@PathVariable Long id) {
        return describe(entityManager.find(ValorEntity.class, id));
    }

    @GetMapping("/debug/parametro/{id}")
    public String debugParametro(@PathVariable Long id) {
        ParametroEntity parametro = entityManager.find(ParametroEntity.class, id);
        if (parametro == null) {
            return "null parametro";
        }
        return "parametro.id_valor via association -> " + describe(parametro.getObjValorSeleccionado());
    }
}

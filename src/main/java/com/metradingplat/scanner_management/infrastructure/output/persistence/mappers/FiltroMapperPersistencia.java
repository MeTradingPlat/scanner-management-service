package com.metradingplat.scanner_management.infrastructure.output.persistence.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;
import com.metradingplat.scanner_management.domain.models.ValorFloat;
import com.metradingplat.scanner_management.domain.models.ValorInteger;
import com.metradingplat.scanner_management.domain.models.ValorString;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.FiltroEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ParametroEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorCondicionalEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorFloatEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorIntegerEntity;
import com.metradingplat.scanner_management.infrastructure.output.persistence.entities.ValorStringEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FiltroMapperPersistencia {

    // Entity -> Dominio
    Filtro mappearEntityAFiltro(FiltroEntity entity);

    @Mapping(target = "objValorSeleccionado", source = "objValorSeleccionado")
    Parametro mappearEntityAParametro(ParametroEntity entity);

    @SubclassMapping(source = ValorIntegerEntity.class, target = ValorInteger.class)
    @SubclassMapping(source = ValorFloatEntity.class, target = ValorFloat.class)
    @SubclassMapping(source = ValorStringEntity.class, target = ValorString.class)
    @SubclassMapping(source = ValorCondicionalEntity.class, target = ValorCondicional.class)
    Valor mappearEntityAValor(ValorEntity entity);

    ValorInteger mappearEntityAValorInteger(ValorIntegerEntity entity);

    ValorFloat mappearEntityAValorFloat(ValorFloatEntity entity);

    ValorString mappearEntityAValorString(ValorStringEntity entity);

    ValorCondicional mappearEntityAValorCondicional(ValorCondicionalEntity entity);

    // Dominio -> Entity
    @InheritInverseConfiguration(name = "mappearEntityAFiltro")
    FiltroEntity mappearFiltroAEntity(Filtro filtro);

    @InheritInverseConfiguration(name = "mappearEntityAParametro")
    @Mapping(target = "objValorSeleccionado", source = "objValorSeleccionado")
    ParametroEntity mappearParametroAEntity(Parametro parametro);

    ValorIntegerEntity mappearValorIntegerAEntity(ValorInteger valor);

    ValorFloatEntity mappearValorFloatAEntity(ValorFloat valor);

    ValorStringEntity mappearValorStringAEntity(ValorString valor);

    ValorCondicionalEntity mappearValorCondicionalAEntity(ValorCondicional valor);

    // Factory: evita return abstract
    default ValorEntity mappearValorAEntity(Valor valor) {
        if (valor instanceof ValorInteger v)
            return mappearValorIntegerAEntity(v);
        if (valor instanceof ValorFloat v)
            return mappearValorFloatAEntity(v);
        if (valor instanceof ValorString v)
            return mappearValorStringAEntity(v);
        if (valor instanceof ValorCondicional v)
            return mappearValorCondicionalAEntity(v);
        throw new IllegalArgumentException("Tipo de Valor desconocido: " + valor.getClass().getName());
    }

    // Listas
    List<Filtro> mappearListaEntityAFiltro(List<FiltroEntity> entities);

    List<FiltroEntity> mappearListaFiltroAEntity(List<Filtro> filtros);

    List<Parametro> mappearListaEntityAParametro(List<ParametroEntity> entities);

    List<ParametroEntity> mappearListaParametroAEntity(List<Parametro> parametros);
}

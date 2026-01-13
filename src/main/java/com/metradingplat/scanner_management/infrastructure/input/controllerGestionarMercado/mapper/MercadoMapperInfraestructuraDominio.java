package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.scanner_management.domain.models.Mercado;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition.MercadoDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarMercado.DTOAnswer.MercadoDTORespuesta;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MercadoMapperInfraestructuraDominio {

    // --- Petición -> Dominio ---
    Mercado mappearDePeticionAMercado(MercadoDTOPeticion peticion);

    List<Mercado> mappearListaDePeticionAMercado(List<MercadoDTOPeticion> peticiones);

    // --- Dominio -> Respuesta ---
    MercadoDTORespuesta mappearDeMercadoARespuesta(Mercado mercado);

    List<MercadoDTORespuesta> mappearListaDeMercadoARespuesta(List<Mercado> mercados);
}
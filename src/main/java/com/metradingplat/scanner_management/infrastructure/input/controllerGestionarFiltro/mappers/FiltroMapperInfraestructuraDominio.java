package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorCondicional;
import com.metradingplat.scanner_management.domain.models.ValorFloat;
import com.metradingplat.scanner_management.domain.models.ValorInteger;
import com.metradingplat.scanner_management.domain.models.ValorString;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.CategoriaDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.FiltroDtoRespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ParametroDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorCondicionalDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorFloatDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorIntegerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorStringDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.FiltroDtoPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ParametroDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ValorCondicionalDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ValorDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ValorFloatDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ValorIntegerDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.ValorStringDTOPeticion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FiltroMapperInfraestructuraDominio {

    // --- Filtro ---
    Filtro mappearDePeticionAFiltro(FiltroDtoPeticion peticion);

    List<Filtro> mappearListaDePeticionAFiltro(List<FiltroDtoPeticion> peticiones);

    FiltroDtoRespuesta mappearDeFiltroARespuesta(Filtro filtro);

    List<FiltroDtoRespuesta> mappearListaDeFiltroARespuesta(List<Filtro> filtros);

    // --- Parámetro ---
    Parametro mappearDePeticionAParametro(ParametroDTOPeticion peticion);

    List<Parametro> mappearListaDePeticionAParametro(List<ParametroDTOPeticion> peticiones);

    ParametroDTORespuesta mappearDeParametroARespuesta(Parametro parametro);

    List<ParametroDTORespuesta> mappearListaDeParametroARespuesta(List<Parametro> parametros);

    // --- Valor genérico ---
    @SubclassMapping(source = ValorIntegerDTOPeticion.class, target = ValorInteger.class)
    @SubclassMapping(source = ValorFloatDTOPeticion.class, target = ValorFloat.class)
    @SubclassMapping(source = ValorStringDTOPeticion.class, target = ValorString.class)
    @SubclassMapping(source = ValorCondicionalDTOPeticion.class, target = ValorCondicional.class)
    Valor mappearDePeticionAValor(ValorDTOPeticion peticion);

    List<Valor> mappearListaDePeticionAValor(List<ValorDTOPeticion> peticiones);

    @SubclassMapping(source = ValorInteger.class, target = ValorIntegerDTORespuesta.class)
    @SubclassMapping(source = ValorFloat.class, target = ValorFloatDTORespuesta.class)
    @SubclassMapping(source = ValorString.class, target = ValorStringDTORespuesta.class)
    @SubclassMapping(source = ValorCondicional.class, target = ValorCondicionalDTORespuesta.class)
    ValorDTORespuesta mappearDeValorARespuesta(Valor valor);

    List<ValorDTORespuesta> mappearListaDeValorARespuesta(List<Valor> valores);

    // --- ValorCondicional ---
    ValorCondicional mappearDePeticionAValorCondicional(ValorCondicionalDTOPeticion peticion);

    List<ValorCondicional> mappearListaDePeticionAValorCondicional(List<ValorCondicionalDTOPeticion> peticiones);

    ValorCondicionalDTORespuesta mappearDeValorCondicionalARespuesta(ValorCondicional valor);

    List<ValorCondicionalDTORespuesta> mappearListaDeValorCondicionalARespuesta(List<ValorCondicional> valores);

    // --- ValorFloat ---
    ValorFloat mappearDePeticionAValorFloat(ValorFloatDTOPeticion peticion);

    List<ValorFloat> mappearListaDePeticionAValorFloat(List<ValorFloatDTOPeticion> peticiones);

    ValorFloatDTORespuesta mappearDeValorFloatARespuesta(ValorFloat valor);

    List<ValorFloatDTORespuesta> mappearListaDeValorFloatARespuesta(List<ValorFloat> valores);

    // --- ValorInteger ---
    ValorInteger mappearDePeticionAValorInteger(ValorIntegerDTOPeticion peticion);

    List<ValorInteger> mappearListaDePeticionAValorInteger(List<ValorIntegerDTOPeticion> peticiones);

    ValorIntegerDTORespuesta mappearDeValorIntegerARespuesta(ValorInteger valor);

    List<ValorIntegerDTORespuesta> mappearListaDeValorIntegerARespuesta(List<ValorInteger> valores);

    // --- ValorString ---
    ValorString mappearDePeticionAValorString(ValorStringDTOPeticion peticion);

    List<ValorString> mappearListaDePeticionAValorString(List<ValorStringDTOPeticion> peticiones);

    ValorStringDTORespuesta mappearDeValorStringARespuesta(ValorString valor);

    List<ValorStringDTORespuesta> mappearListaDeValorStringARespuesta(List<ValorString> valores);

    // --- Categoría ---
    CategoriaFiltro mappearDeRespuestaACategoriaFiltro(CategoriaDTORespuesta respuesta);

    CategoriaDTORespuesta mappearDeCategoriaFiltroARespuesta(CategoriaFiltro categoriaFiltro);

    List<CategoriaDTORespuesta> mappearListaDeCategoriaFiltroARespuesta(List<CategoriaFiltro> categorías);
}

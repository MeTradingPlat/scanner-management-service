package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.input.GestionarFiltroCUIntPort;
import com.metradingplat.scanner_management.application.output.FuenteMensajesIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.CategoriaDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.FiltroDtoRespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOPetition.FiltroDtoPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.mappers.FiltroMapperInfraestructuraDominio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escaner/filtro")
@RequiredArgsConstructor
@Validated
public class FiltroRestController {

    private final GestionarFiltroCUIntPort objGestionarFiltroCUInt;
    private final FiltroMapperInfraestructuraDominio objMapper;
    private final FuenteMensajesIntPort objFuenteMensajes;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTORespuesta>> obtenerCategorias() {
        List<CategoriaFiltro> categorias = this.objGestionarFiltroCUInt.obtenerCategorias();
        List<CategoriaDTORespuesta> categoriaDTO = this.objMapper.mappearListaDeCategoriaFiltroARespuesta(categorias);
        this.objFuenteMensajes.internacionalizarCategorias(categoriaDTO);
        return ResponseEntity.ok(categoriaDTO);
    }

    @GetMapping
    public ResponseEntity<List<FiltroDtoRespuesta>> obtenerFiltrosPorCategoria(
            @RequestParam("categoria") @NotNull(message = "validation.enum.invalid") EnumCategoriaFiltro categoria) {
        List<Filtro> filtros = this.objGestionarFiltroCUInt.obtenerFiltrosPorCategoria(categoria);
        List<FiltroDtoRespuesta> filtrosDTO = this.objMapper.mappearListaDeFiltroARespuesta(filtros);
        this.objFuenteMensajes.internacionalizarFiltros(filtrosDTO);
        return ResponseEntity.ok(filtrosDTO);
    }

    @GetMapping("/defecto")
    public ResponseEntity<FiltroDtoRespuesta> obtenerFiltroPorDefecto(
            @RequestParam("filtro") @NotNull(message = "validation.enum.invalid") EnumFiltro enumFiltro) {
        Filtro filtro = this.objGestionarFiltroCUInt.obtenerFiltroPorDefecto(enumFiltro);
        FiltroDtoRespuesta filtroDTO = this.objMapper.mappearDeFiltroARespuesta(filtro);
        this.objFuenteMensajes.internacionalizarFiltro(filtroDTO);
        return ResponseEntity.ok(filtroDTO);
    }

    @GetMapping("/escaner/{idEscaner}")
    public ResponseEntity<List<FiltroDtoRespuesta>> obtenerFiltrosEscaner(
            @PathVariable("idEscaner") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long idEscaner) {
        List<Filtro> filtros = this.objGestionarFiltroCUInt.obtenerFiltros(idEscaner);
        List<FiltroDtoRespuesta> filtrosDTO = this.objMapper.mappearListaDeFiltroARespuesta(filtros);
        this.objFuenteMensajes.internacionalizarFiltros(filtrosDTO);
        return ResponseEntity.ok(filtrosDTO);
    }

    @PostMapping("/escaner/{idEscaner}")
    public ResponseEntity<List<FiltroDtoRespuesta>> guardarFiltrosDeEscaner(
            @PathVariable("idEscaner") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long idEscaner,
            @RequestBody @Valid @NotNull(message = "validation.filter.request.required") List<FiltroDtoPeticion> peticiones) {
        List<Filtro> filtros = this.objMapper.mappearListaDePeticionAFiltro(peticiones);
        List<Filtro> guardados = this.objGestionarFiltroCUInt.guardarFiltros(idEscaner, filtros);
        List<FiltroDtoRespuesta> filtrosDTO = this.objMapper.mappearListaDeFiltroARespuesta(guardados);
        this.objFuenteMensajes.internacionalizarFiltros(filtrosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(filtrosDTO);
    }

}

package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.input.GestionarEscanerCUIntPort;
import com.metradingplat.scanner_management.application.output.FuenteMensajesIntPort;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EscanerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOPetition.EscanerDTOPeticion;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.mapper.EscanerMapperInfraestructuraDominio;

@RestController
@RequestMapping("/api/escaner")
@RequiredArgsConstructor
@Validated
public class EscanerRestController {

    private final GestionarEscanerCUIntPort objGestionarEscanerCUInt;
    private final EscanerMapperInfraestructuraDominio objMapper;
    private final FuenteMensajesIntPort objFuenteMensajes;

    @PostMapping
    public ResponseEntity<EscanerDTORespuesta> crearEscaner(
            @RequestBody @Valid @NotNull(message = "validation.scanner.request.required") EscanerDTOPeticion peticion) {
        Escaner creado = this.objGestionarEscanerCUInt.crearEscaner(objMapper.mappearDePeticionAEscaner(peticion));
        EscanerDTORespuesta dtoRespuesta = this.objMapper.mappearDeEscanerARespuesta(creado);
        this.objFuenteMensajes.internacionalizarEscaner(dtoRespuesta);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoRespuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscanerDTORespuesta> obtenerEscanerPorId(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        Escaner escaner = this.objGestionarEscanerCUInt.obtenerEscanerPorId(id);
        EscanerDTORespuesta dtoRespuesta = this.objMapper.mappearDeEscanerARespuesta(escaner);
        this.objFuenteMensajes.internacionalizarEscaner(dtoRespuesta);
        return ResponseEntity.ok(dtoRespuesta);
    }

    @GetMapping
    public ResponseEntity<List<EscanerDTORespuesta>> listarEscaneres() {
        List<EscanerDTORespuesta> listaDtoRespuesta = this.objMapper
                .mappearListaDeEscanerARespuesta(this.objGestionarEscanerCUInt.listarEscaneres());
        this.objFuenteMensajes.internacionalizarEscaneres(listaDtoRespuesta);
        return ResponseEntity.ok(listaDtoRespuesta);
    }

    @GetMapping("/archivados")
    public ResponseEntity<List<EscanerDTORespuesta>> listarEscaneresArchivados() {
        List<EscanerDTORespuesta> listaDtoRespuesta = this.objMapper
                .mappearListaDeEscanerARespuesta(this.objGestionarEscanerCUInt.listarEscaneresArchivados());
        this.objFuenteMensajes.internacionalizarEscaneres(listaDtoRespuesta);
        return ResponseEntity.ok(listaDtoRespuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscanerDTORespuesta> actualizarEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id,
            @RequestBody @Valid @NotNull(message = "validation.scanner.request.required") EscanerDTOPeticion peticion) {
        Escaner actualizado = this.objMapper.mappearDePeticionAEscaner(peticion);
        actualizado.setIdEscaner(id);
        Escaner escaner = this.objGestionarEscanerCUInt.actualizarEscaner(actualizado);
        EscanerDTORespuesta dtoRespuesta = this.objMapper.mappearDeEscanerARespuesta(escaner);
        this.objFuenteMensajes.internacionalizarEscaner(dtoRespuesta);
        return ResponseEntity.ok(dtoRespuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        Boolean esEliminado = this.objGestionarEscanerCUInt.eliminarEscaner(id);
        return ResponseEntity.ok(esEliminado);
    }
}

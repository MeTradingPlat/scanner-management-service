package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.domain.models.EstadoEscaner;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EstadoEscanerDTORespuesta;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.mapper.EscanerMapperInfraestructuraDominio;

@RestController
@RequestMapping("/api/escaner/estado")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EstadoEscanerRestController {

    private final GestionarEstadoEscanerCUIntPort objGestionarEstadoEscanerCUInt;
    private final EscanerMapperInfraestructuraDominio objMapper;

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<EstadoEscanerDTORespuesta> iniciarEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        log.info("[CONTROLLER] POST /api/escaner/estado/{}/iniciar - Solicitud recibida", id);
        try {
            EstadoEscaner estado = this.objGestionarEstadoEscanerCUInt.iniciarEscaner(id);
            EstadoEscanerDTORespuesta dto = objMapper.mappearDeEstadoEscanerARespuesta(estado);
            log.info("[CONTROLLER] POST /api/escaner/estado/{}/iniciar - Respuesta OK: estado={}", id, dto.getEnumEstadoEscaner());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("[CONTROLLER] POST /api/escaner/estado/{}/iniciar - ERROR: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{id}/detener")
    public ResponseEntity<EstadoEscanerDTORespuesta> detenerEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        log.info("[CONTROLLER] POST /api/escaner/estado/{}/detener - Solicitud recibida", id);
        try {
            EstadoEscaner estado = this.objGestionarEstadoEscanerCUInt.detenerEscaner(id);
            EstadoEscanerDTORespuesta dto = objMapper.mappearDeEstadoEscanerARespuesta(estado);
            log.info("[CONTROLLER] POST /api/escaner/estado/{}/detener - Respuesta OK: estado={}", id, dto.getEnumEstadoEscaner());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("[CONTROLLER] POST /api/escaner/estado/{}/detener - ERROR: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{id}/archivar")
    public ResponseEntity<EstadoEscanerDTORespuesta> archivarEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        log.info("[CONTROLLER] POST /api/escaner/estado/{}/archivar - Solicitud recibida", id);
        try {
            EstadoEscaner estado = this.objGestionarEstadoEscanerCUInt.archivarEscaner(id);
            EstadoEscanerDTORespuesta dto = objMapper.mappearDeEstadoEscanerARespuesta(estado);
            log.info("[CONTROLLER] POST /api/escaner/estado/{}/archivar - Respuesta OK: estado={}", id, dto.getEnumEstadoEscaner());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("[CONTROLLER] POST /api/escaner/estado/{}/archivar - ERROR: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{id}/desarchivar")
    public ResponseEntity<EstadoEscanerDTORespuesta> desarchivarEscaner(
            @PathVariable("id") @NotNull(message = "validation.type.positive.required") @Positive(message = "validation.type.positive.required") Long id) {
        log.info("[CONTROLLER] POST /api/escaner/estado/{}/desarchivar - Solicitud recibida", id);
        try {
            EstadoEscaner estado = this.objGestionarEstadoEscanerCUInt.desarchivarEscaner(id);
            EstadoEscanerDTORespuesta dto = objMapper.mappearDeEstadoEscanerARespuesta(estado);
            log.info("[CONTROLLER] POST /api/escaner/estado/{}/desarchivar - Respuesta OK: estado={}", id, dto.getEnumEstadoEscaner());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("[CONTROLLER] POST /api/escaner/estado/{}/desarchivar - ERROR: {}", id, e.getMessage());
            throw e;
        }
    }
}

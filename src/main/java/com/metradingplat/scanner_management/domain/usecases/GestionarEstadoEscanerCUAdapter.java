package com.metradingplat.scanner_management.domain.usecases;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Escaner;
import com.metradingplat.scanner_management.domain.models.EstadoEscaner;
import com.metradingplat.scanner_management.domain.states.GestorEstadoEscaner;
import com.metradingplat.scanner_management.domain.states.escaner.ResultadoGestorEscaner;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;

@RequiredArgsConstructor
public class GestionarEstadoEscanerCUAdapter implements GestionarEstadoEscanerCUIntPort {

    private final GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort;
    private final GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort;
    private final FormateadorResultadosIntPort objFormateadorResultados;
    private final FuenteMensajesSignalProcessingIntPort objFuenteMensajesSignalProcessing;

    @Override
    public EstadoEscaner iniciarEscaner(Long id) {
        Escaner escaner = validarEscanerExistente(id);

        if (escaner.getFiltros() == null || escaner.getFiltros().isEmpty()) {
            this.objFormateadorResultados.errorReglaNegocioViolada("validation.scanner.filters.required");
        }

        EstadoEscaner estado = cambiarEstado(escaner, EnumEstadoEscaner.INICIADO);
        this.objFuenteMensajesSignalProcessing.notificarEscanerIniciado(escaner);
        return estado;
    }

    @Override
    public EstadoEscaner detenerEscaner(Long id) {
        EstadoEscaner estado = cambiarEstado(validarEscanerExistente(id), EnumEstadoEscaner.DETENIDO);
        this.objFuenteMensajesSignalProcessing.notificarEscanerDetenido(id);
        return estado;
    }

    @Override
    public EstadoEscaner archivarEscaner(Long id) {
        return cambiarEstado(validarEscanerExistente(id), EnumEstadoEscaner.ARCHIVADO);
    }

    @Override
    public EstadoEscaner desarchivarEscaner(Long id) {
        return cambiarEstado(validarEscanerExistente(id), EnumEstadoEscaner.DESARCHIVADO);
    }

    private Escaner validarEscanerExistente(Long id) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(id)) {
            this.objFormateadorResultados.errorEntidadNoExiste("validation.scanner.id.notFound", id);
        }
        return this.objGestionarEscanerGatewayIntPort.obtenerEscanerPorId(id);
    }

    private EstadoEscaner cambiarEstado(Escaner escaner, EnumEstadoEscaner nuevoEstado) {
        EnumEstadoEscaner estadoActual = escaner.getObjEstado().getEnumEstadoEscaner();
        GestorEstadoEscaner gestorEstado = new GestorEstadoEscaner(estadoActual);
        ResultadoGestorEscaner resultado = gestorEstado.cambiarEstado(nuevoEstado);

        if (!resultado.cambioPermitido()) {
            this.objFormateadorResultados.errorEstadoDenegado(resultado.mensaje());
        }

        return this.objGestionarEstadoEscanerGatewayIntPort.cambiarEstadoDeEscaner(escaner, nuevoEstado);
    }
}

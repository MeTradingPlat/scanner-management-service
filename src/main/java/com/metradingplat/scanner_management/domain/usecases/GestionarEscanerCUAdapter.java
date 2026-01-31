package com.metradingplat.scanner_management.domain.usecases;

import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.input.GestionarEscanerCUIntPort;
import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Escaner;

import java.util.List;

@RequiredArgsConstructor
public class GestionarEscanerCUAdapter implements GestionarEscanerCUIntPort {

    private final GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort;
    private final GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;

    @Override
    public Escaner crearEscaner(Escaner objEscaner) {
        if (this.objGestionarEscanerGatewayIntPort.existeEscanerPorNombre(objEscaner.getNombre())) {
            this.objFormateadorResultadosIntPort.errorEntidadYaExiste("validation.scanner.name.exists");
        }
        if (objEscaner.getHoraInicio().isAfter(objEscaner.getHoraFin())) {
            this.objFormateadorResultadosIntPort.errorReglaNegocioViolada("validation.scanner.schedule.invalid");
        }
        if (objEscaner.getMercados() == null || objEscaner.getMercados().isEmpty()) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.market.required");
        }
        Escaner escanerGuardado = this.objGestionarEscanerGatewayIntPort.crearEscaner(objEscaner);
        escanerGuardado.setObjEstado(this.objGestionarEstadoEscanerGatewayIntPort
                .cambiarEstadoDeEscaner(escanerGuardado, EnumEstadoEscaner.DETENIDO));
        return escanerGuardado;
    }

    @Override
    public Escaner obtenerEscanerPorId(Long idEscaner) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }
        return this.objGestionarEscanerGatewayIntPort.obtenerEscanerPorId(idEscaner);
    }

    @Override
    public List<Escaner> listarEscaneres() {
        return this.objGestionarEscanerGatewayIntPort.obtenerEscaneresDesarchivados();
    }

    @Override
    public List<Escaner> listarEscaneresArchivados() {
        return this.objGestionarEscanerGatewayIntPort.obtenerEscaneresArchivados();
    }

    @Override
    public List<Escaner> listarEscaneresIniciados() {
        return this.objGestionarEscanerGatewayIntPort.obtenerEscaneresIniciados();
    }

    @Override
    public Escaner actualizarEscaner(Escaner objEscaner) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(objEscaner.getIdEscaner())) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound",
                    objEscaner.getIdEscaner());
        }
        if (this.objGestionarEscanerGatewayIntPort.existeEscanerPorNombre(objEscaner.getIdEscaner(),
                objEscaner.getNombre())) {
            this.objFormateadorResultadosIntPort.errorEntidadYaExiste("validation.scanner.name.exists");
        }
        if (objEscaner.getHoraInicio().isAfter(objEscaner.getHoraFin())) {
            this.objFormateadorResultadosIntPort.errorReglaNegocioViolada("validation.scanner.schedule.invalid");
        }
        if (objEscaner.getObjTipoEjecucion() == null) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.executionType.required");
        }
        if (objEscaner.getMercados() == null || objEscaner.getMercados().isEmpty()) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.market.required");
        }
        return this.objGestionarEscanerGatewayIntPort.actualizarEscaner(objEscaner);
    }

    @Override
    public Boolean eliminarEscaner(Long idEscaner) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }
        Boolean respuesta = this.objGestionarEscanerGatewayIntPort.eliminarEscaner(idEscaner);
        return respuesta;
    }
}

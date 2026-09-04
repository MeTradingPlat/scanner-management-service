package com.metradingplat.scanner_management.domain.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.metradingplat.scanner_management.application.input.GestionarEscanerCUIntPort;
import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.LimpiezaDatosEscanerIntPort;
import com.metradingplat.scanner_management.application.output.NotificacionKafkaProducerIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.models.Escaner;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class GestionarEscanerCUAdapter implements GestionarEscanerCUIntPort {

    private final GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort;
    private final GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;
    private final LimpiezaDatosEscanerIntPort objLimpiezaDatosEscaner;
    private final GestionarEstadoEscanerCUIntPort objGestionarEstadoEscanerCUIntPort;
    private final NotificacionKafkaProducerIntPort objNotificacionProducer;

    @Override
    public Escaner crearEscaner(Escaner objEscaner) {
        if (this.objGestionarEscanerGatewayIntPort.existeEscanerPorNombre(objEscaner.getNombre())) {
            this.objFormateadorResultadosIntPort.errorEntidadYaExiste("validation.scanner.name.exists");
        }
        if (objEscaner.getMercados() == null || objEscaner.getMercados().isEmpty()) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.market.required");
        }
        Escaner escanerGuardado = this.objGestionarEscanerGatewayIntPort.crearEscaner(objEscaner);
        escanerGuardado.setObjEstado(this.objGestionarEstadoEscanerGatewayIntPort
                .cambiarEstadoDeEscaner(escanerGuardado, EnumEstadoEscaner.DETENIDO));
        // Notifica a todos los clientes conectados (viewers con la lista
        // abierta) que hay un escaner nuevo -- sin esto, el unico camino para
        // verlo era recargar la pagina (ver notificacion-sse.service.ts,
        // handleStateChange solo actualizaba escaneres YA presentes en la
        // lista local, nunca agregaba uno nuevo).
        publicarCambioLista(escanerGuardado.getIdEscaner(), escanerGuardado.getNombre(), "CREADO",
                "Escaner creado", "{\"evento\":\"SCANNER_CREADO\"}");
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
        if (objEscaner.getObjTipoEjecucion() == null) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.executionType.required");
        }
        if (objEscaner.getMercados() == null || objEscaner.getMercados().isEmpty()) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.market.required");
        }

        // Guardar un escaner INICIADO ya no se rechaza (antes GC-0005): el
        // pipeline de signal-processing carga la config al arrancar, asi que
        // para que los cambios apliquen hay que reiniciarlo -- se detiene, se
        // guarda y se vuelve a iniciar. Si el guardado falla, se reinicia
        // igual (con la config anterior) para no dejar el escaner apagado
        // por un error de edicion.
        EnumEstadoEscaner estadoActual = this.objGestionarEstadoEscanerGatewayIntPort
                .obtenerEstadoDeEscanerActual(objEscaner.getIdEscaner());
        boolean estabaIniciado = estadoActual == EnumEstadoEscaner.INICIADO;
        if (estabaIniciado) {
            log.info("[USE-CASE] actualizarEscaner - escaner INICIADO, deteniendo para editar, id={}",
                    objEscaner.getIdEscaner());
            this.objGestionarEstadoEscanerCUIntPort.detenerEscaner(objEscaner.getIdEscaner());
        }
        try {
            Escaner escanerGuardado = this.objGestionarEscanerGatewayIntPort.actualizarEscaner(objEscaner);
            if (estabaIniciado) {
                log.info("[USE-CASE] actualizarEscaner - guardado OK, reiniciando escaner, id={}",
                        objEscaner.getIdEscaner());
                escanerGuardado.setObjEstado(
                        this.objGestionarEstadoEscanerCUIntPort.iniciarEscaner(objEscaner.getIdEscaner()));
            }
            return escanerGuardado;
        } catch (Exception e) {
            if (estabaIniciado) {
                log.warn("[USE-CASE] actualizarEscaner - guardado fallo, reiniciando con config anterior, id={}",
                        objEscaner.getIdEscaner(), e);
                try {
                    this.objGestionarEstadoEscanerCUIntPort.iniciarEscaner(objEscaner.getIdEscaner());
                } catch (Exception reinicioFallido) {
                    log.error("[USE-CASE] actualizarEscaner - ademas fallo el reinicio, escaner queda detenido, id={}",
                            objEscaner.getIdEscaner(), reinicioFallido);
                }
            }
            throw e;
        }
    }

    @Override
    public Boolean eliminarEscaner(Long idEscaner) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }
        validarEstadoPermiteEliminacion(idEscaner);
        Escaner escaner = this.objGestionarEscanerGatewayIntPort.obtenerEscanerPorId(idEscaner);
        // Limpiar datos relacionados en otros servicios antes de eliminar
        this.objLimpiezaDatosEscaner.eliminarLogsPorEscaner(idEscaner);
        Boolean respuesta = this.objGestionarEscanerGatewayIntPort.eliminarEscaner(idEscaner);
        // Mismo motivo que en crearEscaner: sin esto, un viewer con la lista
        // abierta seguia viendo el escaner eliminado hasta recargar.
        publicarCambioLista(idEscaner, escaner.getNombre(), "ELIMINADO",
                "Escaner eliminado", "{\"evento\":\"SCANNER_ELIMINADO\"}");
        return respuesta;
    }

    private void validarEstadoPermiteEliminacion(Long idEscaner) {
        EnumEstadoEscaner estadoActual = this.objGestionarEstadoEscanerGatewayIntPort
                .obtenerEstadoDeEscanerActual(idEscaner);
        if (estadoActual == EnumEstadoEscaner.INICIADO) {
            this.objFormateadorResultadosIntPort.errorEstadoDenegado(
                    "validation.scanner.state.cannotDeleteWhileRunning");
        }
    }

    // publicarCambioLista reusa el mismo canal SSE que ya usa
    // GestionarEstadoEscanerCUAdapter para iniciar/detener (tipo=
    // SCANNER_STATE, metadatos.estadoNuevo) -- "CREADO"/"ELIMINADO" no son
    // estados reales de EnumEstadoEscaner, son solo el valor que el frontend
    // lee de metadatos para decidir si agrega o quita la fila de su lista en
    // memoria (ver scanner-list.ts, handleStateChange) en vez de pisar el
    // estado real del escaner.
    private void publicarCambioLista(Long idEscaner, String nombreEscaner, String estadoNuevo, String mensaje,
            String metadatos) {
        this.objNotificacionProducer.publicarNotificacion(idEscaner, "SCANNER_STATE", "INFO", mensaje, "SCANNER",
                metadatos);
        this.objNotificacionProducer.publicarCambioEstadoEscaner(idEscaner, nombreEscaner, "", estadoNuevo, mensaje);
    }
}

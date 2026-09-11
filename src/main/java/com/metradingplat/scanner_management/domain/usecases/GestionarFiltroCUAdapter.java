package com.metradingplat.scanner_management.domain.usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.metradingplat.scanner_management.application.input.GestionarEstadoEscanerCUIntPort;
import com.metradingplat.scanner_management.application.input.GestionarFiltroCUIntPort;
import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumEstadoEscaner;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.CategoriaFiltro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Parametro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;

@RequiredArgsConstructor
@Slf4j
public class GestionarFiltroCUAdapter implements GestionarFiltroCUIntPort {

    private final GestionarFiltroGatewayIntPort objGestionarFiltroGatewayIntPort;
    private final GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort;
    private final GestorEstrategiaFiltroIntPort objGestorFactoryFiltro;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;
    private final GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort;
    private final GestionarEstadoEscanerCUIntPort objGestionarEstadoEscanerCUIntPort;

    @Override
    public List<CategoriaFiltro> obtenerCategorias() {
        return Arrays.stream(EnumCategoriaFiltro.values())
                .map(enumCat -> new CategoriaFiltro(
                        enumCat.getEtiqueta(),
                        enumCat))
                .collect(Collectors.toList());
    }

    @Override
    public List<Filtro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro enumCategoria) {
        List<EnumFiltro> enumsFiltro = this.objGestorFactoryFiltro.obtenerFiltrosPorCategoria(enumCategoria);
        if (enumsFiltro.size() == 1 && enumsFiltro.get(0) == EnumFiltro.UNKNOWN) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.filter.category.notFound");
        }
        return enumsFiltro.stream()
                .map(this.objGestorFactoryFiltro::obtenerInfomracionFiltro)
                .collect(Collectors.toList());
    }

    @Override
    public Filtro obtenerFiltroPorDefecto(EnumFiltro enumFiltro) {
        if (!this.objGestorFactoryFiltro.validarEnumFiltro(enumFiltro)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.filter.type.notFound");
        }
        return this.objGestorFactoryFiltro.obtenerFiltroConValoresPorDefecto(enumFiltro);
    }

    @Override
    public List<Filtro> obtenerFiltros(Long idEscaner) {
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }

        List<Filtro> filtrosGuardados = this.objGestionarFiltroGatewayIntPort.obtenerFiltrosGuardados(idEscaner);
        List<Filtro> filtrosARetornar = new ArrayList<Filtro>();

        for (Filtro filtro : filtrosGuardados) {
            Map<EnumParametro, Valor> valoresSeleccionados = extraerValoresSeleccionados(filtro);

            // crearFiltroConValoresSeleccionados reconstruye el Filtro desde
            // la fabrica (enumFiltro + parametros) -- no conoce
            // revisionTiempoReal, que solo vive en la entidad guardada, asi
            // que hay que copiarlo aparte o se pierde en cada lectura.
            Filtro filtroReconstruido = objGestorFactoryFiltro.crearFiltroConValoresSeleccionados(
                    filtro.getEnumFiltro(), valoresSeleccionados);
            filtroReconstruido.setRevisionTiempoReal(filtro.isRevisionTiempoReal());
            filtroReconstruido.setGrupoAlternativo(filtro.getGrupoAlternativo());
            filtrosARetornar.add(filtroReconstruido);
        }

        return filtrosARetornar;
    }

    @Override
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros) {
        if (!objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }

        List<ResultadoValidacion> errores = new ArrayList<ResultadoValidacion>();
        List<Filtro> filtrosCreados = new ArrayList<Filtro>();

        for (Filtro filtro : filtros) {
            Map<EnumParametro, Valor> valoresSeleccionados = extraerValoresSeleccionados(filtro);

            List<ResultadoValidacion> erroresFiltro = objGestorFactoryFiltro.validarValoresSeleccionados(
                    filtro.getEnumFiltro(), valoresSeleccionados);

            if (!erroresFiltro.isEmpty()) {
                errores.addAll(erroresFiltro);
                continue;
            }

            // Mismo caso que obtenerFiltros: la fabrica no conoce
            // revisionTiempoReal (viene del request, no de los parametros),
            // asi que se pierde si no se copia desde el filtro original.
            Filtro filtroCreado = objGestorFactoryFiltro.crearFiltroConValoresSeleccionados(
                    filtro.getEnumFiltro(), valoresSeleccionados);
            filtroCreado.setRevisionTiempoReal(filtro.isRevisionTiempoReal());
            filtroCreado.setGrupoAlternativo(filtro.getGrupoAlternativo());
            filtrosCreados.add(filtroCreado);
        }

        if (!errores.isEmpty()) {
            objFormateadorResultadosIntPort.errorValidacionFiltro(errores);
        }

        // Mismo motivo que GestionarEscanerCUAdapter.actualizarEscaner: el
        // pipeline de signal-processing carga los filtros al arrancar y nunca
        // los vuelve a pedir, asi que guardar cambios sobre un escaner
        // INICIADO no tenia ningun efecto hasta que el usuario lo paraba y
        // arrancaba a mano (confirmado en vivo: un escaner con
        // revisionTiempoReal en curso siguio evaluando con la config vieja
        // despues de guardar un filtro nuevo). Se detiene, se guarda y se
        // vuelve a iniciar -- si el guardado falla, se reinicia igual con la
        // config anterior para no dejar el escaner apagado por un error de
        // edicion.
        EnumEstadoEscaner estadoActual = objGestionarEstadoEscanerGatewayIntPort
                .obtenerEstadoDeEscanerActual(idEscaner);
        boolean estabaIniciado = estadoActual == EnumEstadoEscaner.INICIADO;
        if (estabaIniciado) {
            log.info("[USE-CASE] guardarFiltros - escaner INICIADO, deteniendo para editar, id={}", idEscaner);
            objGestionarEstadoEscanerCUIntPort.detenerEscaner(idEscaner);
        }
        try {
            objGestionarFiltroGatewayIntPort.guardarFiltros(idEscaner, filtrosCreados);
            if (estabaIniciado) {
                log.info("[USE-CASE] guardarFiltros - guardado OK, reiniciando escaner, id={}", idEscaner);
                objGestionarEstadoEscanerCUIntPort.iniciarEscaner(idEscaner);
            }
            return filtrosCreados;
        } catch (Exception e) {
            if (estabaIniciado) {
                log.warn("[USE-CASE] guardarFiltros - guardado fallo, reiniciando con config anterior, id={}",
                        idEscaner, e);
                try {
                    objGestionarEstadoEscanerCUIntPort.iniciarEscaner(idEscaner);
                } catch (Exception reinicioFallido) {
                    log.error("[USE-CASE] guardarFiltros - ademas fallo el reinicio, escaner queda detenido, id={}",
                            idEscaner, reinicioFallido);
                }
            }
            throw e;
        }
    }

    private Map<EnumParametro, Valor> extraerValoresSeleccionados(Filtro filtro) {
        return filtro.getParametros().stream()
                .collect(Collectors.toMap(
                        Parametro::getEnumParametro,
                        Parametro::getObjValorSeleccionado));
    }
}
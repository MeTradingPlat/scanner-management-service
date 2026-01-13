package com.metradingplat.scanner_management.infrastructure.business.strategies;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.Getter;

import com.metradingplat.scanner_management.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;
import com.metradingplat.scanner_management.domain.models.Filtro;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacion;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.EntidadNoExisteException;

/**
 * Gestor de factorías de filtros que actúa como Registry y Facade.
 *
 * <p>
 * <b>Patrones de Diseño:</b>
 * <ul>
 * <li><b>Registry Pattern:</b> Mantiene un registro de todas las factorías de
 * filtros disponibles</li>
 * <li><b>Facade Pattern:</b> Proporciona una interfaz simplificada para acceder
 * a las factorías</li>
 * </ul>
 *
 * <p>
 * Esta clase gestiona el ciclo de vida y acceso a las 41 implementaciones de
 * {@link IFiltroFactory}. Spring inyecta automáticamente todas las
 * implementaciones
 * anotadas con {@code @Component} y este gestor las indexa por tipo de filtro.
 *
 * <p>
 * <b>Funcionalidades:</b>
 * <ul>
 * <li>Auto-descubrimiento de factorías mediante inyección de Spring</li>
 * <li>Acceso rápido a factorías por EnumFiltro (O(1))</li>
 * <li>Organización de filtros por categoría</li>
 * <li>Delegación de operaciones a la factoría apropiada</li>
 * </ul>
 *
 * @see IFiltroFactory
 * @see GestorEstrategiaFiltroIntPort
 */
@Service
@Getter
public class GestorFiltroFactory implements GestorEstrategiaFiltroIntPort {
    private final Map<EnumFiltro, IFiltroFactory> mapEnumFiltroIFiltroFactory;
    private final Map<EnumCategoriaFiltro, List<EnumFiltro>> mapCategoriaFiltros;

    public GestorFiltroFactory(Set<IFiltroFactory> filtros) {
        this.mapEnumFiltroIFiltroFactory = new HashMap<>();
        this.mapCategoriaFiltros = new EnumMap<>(EnumCategoriaFiltro.class);

        for (IFiltroFactory filtro : filtros) {
            EnumFiltro enumFiltro = filtro.obtenerEnumFiltro();
            EnumCategoriaFiltro categoria = filtro.obtenerEnumCategoria();

            this.mapEnumFiltroIFiltroFactory.put(enumFiltro, filtro);

            this.mapCategoriaFiltros
                    .computeIfAbsent(categoria, k -> new ArrayList<>())
                    .add(enumFiltro);

            this.mapCategoriaFiltros
                    .computeIfAbsent(EnumCategoriaFiltro.TODOS, k -> new ArrayList<>())
                    .add(enumFiltro);
        }
    }

    public IFiltroFactory obtenerEstrategia(EnumFiltro enumFiltro) {
        IFiltroFactory estrategia = mapEnumFiltroIFiltroFactory.get(enumFiltro);
        if (estrategia == null) {
            throw new EntidadNoExisteException("estrategia.filtro.no.existe", enumFiltro.name());
        }
        return estrategia;
    }

    public Map<EnumFiltro, IFiltroFactory> obtenerTodasLasEstrategias() {
        return Map.copyOf(mapEnumFiltroIFiltroFactory);
    }

    @Override
    public List<EnumFiltro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro categoria) {
        return this.mapCategoriaFiltros.getOrDefault(categoria, List.of(EnumFiltro.UNKNOWN));
    }

    @Override
    public Filtro obtenerInfomracionFiltro(EnumFiltro tipoFiltro) {
        return obtenerEstrategia(tipoFiltro).obtenerInformacionFiltro();
    }

    @Override
    public Filtro obtenerFiltroConValoresPorDefecto(EnumFiltro tipoFiltro) {
        return obtenerEstrategia(tipoFiltro).obtenerFiltro();
    }

    @Override
    public Filtro crearFiltroConValoresSeleccionados(EnumFiltro tipoFiltro, Map<EnumParametro, Valor> valores) {
        return obtenerEstrategia(tipoFiltro).obtenerFiltro(valores);
    }

    @Override
    public Boolean validarEnumFiltro(EnumFiltro tipoFiltro) {
        return this.mapEnumFiltroIFiltroFactory.containsKey(tipoFiltro);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(EnumFiltro tipoFiltro,
            Map<EnumParametro, Valor> valores) {
        return obtenerEstrategia(tipoFiltro).validarValoresSeleccionados(valores);
    }
}

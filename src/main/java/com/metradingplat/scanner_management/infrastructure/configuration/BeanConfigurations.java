package com.metradingplat.scanner_management.infrastructure.configuration;

import com.metradingplat.scanner_management.application.output.FormateadorResultadosIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.scanner_management.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.scanner_management.domain.usecases.GestionarEscanerCUAdapter;
import com.metradingplat.scanner_management.domain.usecases.GestionarEstadoEscanerCUAdapter;
import com.metradingplat.scanner_management.domain.usecases.GestionarFiltroCUAdapter;
import com.metradingplat.scanner_management.domain.usecases.GestionarMercadoCUAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metradingplat.scanner_management.application.output.FuenteMensajesSignalProcessingIntPort;
import com.metradingplat.scanner_management.application.output.LimpiezaDatosEscanerIntPort;
import com.metradingplat.scanner_management.application.output.NotificacionKafkaProducerIntPort;

@Configuration
public class BeanConfigurations {

    // Input Port Beans (Use Cases)
    @Bean
    public GestionarEscanerCUAdapter gestionarEscanerCUIntPort(
            GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort,
            GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGateWayIntPort,
            FormateadorResultadosIntPort objFormateadorResultadosIntPort,
            LimpiezaDatosEscanerIntPort objLimpiezaDatosEscaner) {
        return new GestionarEscanerCUAdapter(objGestionarEscanerGatewayIntPort, objGestionarEstadoEscanerGateWayIntPort,
                objFormateadorResultadosIntPort, objLimpiezaDatosEscaner);
    }

    @Bean
    public GestionarEstadoEscanerCUAdapter gestionarEstadoEscanerCUIntPort(
            GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort,
            GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort,
            FormateadorResultadosIntPort objFormateadorResultados,
            FuenteMensajesSignalProcessingIntPort objFuenteMensajesSignalProcessing,
            NotificacionKafkaProducerIntPort objNotificacionProducer) {
        return new GestionarEstadoEscanerCUAdapter(objGestionarEstadoEscanerGatewayIntPort,
                objGestionarEscanerGatewayIntPort, objFormateadorResultados, objFuenteMensajesSignalProcessing,
                objNotificacionProducer);
    }

    @Bean
    public GestionarFiltroCUAdapter gestionarFiltroCUIntPort(
            GestionarFiltroGatewayIntPort objGestionarFiltroGatewayIntPort,
            GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort,
            GestorEstrategiaFiltroIntPort objGestorFactoryFiltro,
            FormateadorResultadosIntPort objFormateadorResultadosIntPort) {
        return new GestionarFiltroCUAdapter(objGestionarFiltroGatewayIntPort, objGestionarEscanerGatewayIntPort,
                objGestorFactoryFiltro, objFormateadorResultadosIntPort);
    }

    @Bean
    public GestionarMercadoCUAdapter gestionarMercadoCUIntPort() {
        return new GestionarMercadoCUAdapter();
    }
}
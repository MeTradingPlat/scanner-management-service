package com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure;

public final class ErrorUtils {

  ErrorUtils() {

  }

  /**
   * Crea un nuevo objeto de `Error`
   * 
   * @param codigoError
   * @param llaveMensaje
   * @param codigoHttp
   * @param url          URL de la petición que generó el error
   * @param metodo       Método HTTP de la petición (GET, POST, etc.)
   * @return - Objeto creado
   */
  public static Error crearError(final String codigoError,
      final String llaveMensaje,
      final Integer codigoHttp,
      final String url,
      final String metodo) {
    final Error error = new Error();
    error.setCodigoError(codigoError);
    error.setMensaje(llaveMensaje);
    error.setCodigoHttp(codigoHttp);
    error.setUrl(url);
    error.setMetodo(metodo);
    return error;
  }
}

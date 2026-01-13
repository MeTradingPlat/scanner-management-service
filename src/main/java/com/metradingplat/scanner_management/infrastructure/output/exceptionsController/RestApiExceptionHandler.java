package com.metradingplat.scanner_management.infrastructure.output.exceptionsController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.metradingplat.scanner_management.application.output.FuenteMensajesIntPort;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.dto.ValidationErrorDetail;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.dto.ValidationErrorResponse;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure.Error;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.exceptionStructure.ErrorUtils;
import com.metradingplat.scanner_management.infrastructure.output.exceptionsController.ownExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final FuenteMensajesIntPort objFuenteMensajes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(HttpServletRequest req, Exception ex) {
        return createErrorResponse(
                CodigoError.ERROR_GENERICO,
                HttpStatus.INTERNAL_SERVER_ERROR,
                req,
                CodigoError.ERROR_GENERICO.getLlaveMensaje());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        return createErrorResponse(
                CodigoError.TIPO_DE_ARGUMENTO_INVALIDO,
                HttpStatus.BAD_REQUEST,
                req,
                CodigoError.TIPO_DE_ARGUMENTO_INVALIDO.getLlaveMensaje());
    }

    /**
     * Maneja errores de deserialización JSON (ej: formatos de fecha/hora
     * inválidos).
     *
     * Se activa cuando Jackson no puede convertir el JSON del request body a los
     * tipos Java
     * esperados, como LocalTime, LocalDate, Integer, etc.
     *
     * Intenta extraer el nombre del campo desde el mensaje de la excepción para
     * proporcionar un error más específico al frontend.
     *
     * @param ex  Excepción de deserialización
     * @param req Request HTTP para información de trazabilidad
     * @return ResponseEntity con código 400 Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpServletRequest req) {
        // Intentar extraer el nombre del campo del mensaje de la excepción
        String fieldName = extractFieldNameFromException(ex);
        String errorMessage = internacionalizarMensaje("error.formato.datos.invalido");

        // Si encontramos el campo, formateamos el mensaje como "campo: mensaje"
        String finalMessage = fieldName != null
                ? fieldName + ": " + errorMessage
                : errorMessage;

        Error error = ErrorUtils.crearError(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(),
                finalMessage,
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURL().toString(),
                req.getMethod());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Intenta extraer el nombre del campo desde el mensaje de la excepción.
     *
     * El mensaje de Jackson típicamente incluye el path del campo en formato:
     * "... through reference chain: ClassName["fieldName"]..."
     *
     * @param ex La excepción de deserialización
     * @return El nombre del campo o null si no se puede determinar
     */
    private String extractFieldNameFromException(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message == null) {
            return null;
        }

        // Buscar patrón: ["nombreCampo"] en el mensaje principal
        int startIdx = message.lastIndexOf("[\"");
        if (startIdx != -1) {
            int endIdx = message.indexOf("\"]", startIdx);
            if (endIdx != -1) {
                return message.substring(startIdx + 2, endIdx);
            }
        }

        // Si no se encontró en el mensaje principal, buscar en la causa raíz
        Throwable cause = ex.getCause();
        while (cause != null) {
            String causeMessage = cause.getMessage();
            if (causeMessage != null) {
                startIdx = causeMessage.lastIndexOf("[\"");
                if (startIdx != -1) {
                    int endIdx = causeMessage.indexOf("\"]", startIdx);
                    if (endIdx != -1) {
                        return causeMessage.substring(startIdx + 2, endIdx);
                    }
                }
            }
            cause = cause.getCause();
        }

        return null;
    }

    @ExceptionHandler(EntidadYaExisteException.class)
    public ResponseEntity<Error> handleEntidadYaExisteException(final HttpServletRequest req,
            final EntidadYaExisteException ex) {
        return createErrorResponse(
                CodigoError.ENTIDAD_YA_EXISTE,
                HttpStatus.NOT_ACCEPTABLE,
                req,
                ex.getMessage(),
                ex.getArgs());
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleEntidadNoExisteException(EntidadNoExisteException ex, HttpServletRequest req) {
        return createErrorResponse(
                CodigoError.ENTIDAD_NO_ENCONTRADA,
                HttpStatus.NOT_ACCEPTABLE,
                req,
                ex.getMessage(),
                ex.getArgs());
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Error> handleReglaNegocioExcepcion(ReglaNegocioException ex, HttpServletRequest req) {
        return createErrorResponse(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO,
                HttpStatus.BAD_REQUEST,
                req,
                ex.getMessage(),
                ex.getArgs());
    }

    @ExceptionHandler(EstadoDenegadoException.class)
    public ResponseEntity<Error> handleEstadoDenegadoException(EstadoDenegadoException ex, HttpServletRequest req) {
        return createErrorResponse(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO,
                HttpStatus.BAD_REQUEST,
                req,
                ex.getMessage(),
                ex.getArgs());
    }

    /**
     * Maneja excepciones de validación de filtros.
     *
     * Convierte los errores de validación del dominio en una respuesta estructurada
     * que incluye contexto completo (filtro + parámetro) para cada error.
     *
     * Esta implementación sigue mejores prácticas:
     * - Respuesta estructurada y consistente con otros errores
     * - Incluye información de trazabilidad (URL, método HTTP)
     * - Mapea correctamente el contexto de filtro para el frontend
     * - Internacionaliza los mensajes de error
     *
     * @param ex  Excepción que contiene los errores de validación con contexto de
     *            filtro
     * @param req Request HTTP para información de trazabilidad
     * @return ResponseEntity con estructura ValidationErrorResponse
     */
    @ExceptionHandler(ValidacionFiltroException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationFiltroExceptions(
            ValidacionFiltroException ex,
            HttpServletRequest req) {

        // Mapear errores de validación a detalles estructurados
        List<ValidationErrorDetail> erroresDetallados = new ArrayList<>();

        ex.getErroresValidacion().forEach(errorValidacion -> {
            String mensajeInternacionalizado = internacionalizarMensaje(
                    errorValidacion.mensaje(),
                    errorValidacion.args());

            ValidationErrorDetail detalle = ValidationErrorDetail.builder()
                    .filtro(errorValidacion.enumFiltro().name())
                    .parametro(errorValidacion.enumParametro().name())
                    .mensaje(mensajeInternacionalizado)
                    .filtroIndex(null) // Se podría calcular si hay múltiples filtros del mismo tipo
                    .build();

            erroresDetallados.add(detalle);
        });

        // Construir respuesta completa usando el builder
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .codigoError(CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo())
                .mensaje(internacionalizarMensaje("error.validacion.filtros"))
                .codigoHttp(HttpStatus.BAD_REQUEST.value())
                .url(req.getRequestURL().toString())
                .metodo(req.getMethod())
                .erroresValidacion(erroresDetallados)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja excepciones de validación de Bean Validation (JSR-380).
     *
     * Se activa cuando las anotaciones @Valid, @NotNull, @NotBlank, @Size, etc.
     * fallan en los DTOs de petición.
     *
     * Devuelve una estructura de error RFC 7807 consistente con el resto del
     * sistema.
     *
     * @param ex  Excepción que contiene los errores de validación
     * @param req Request HTTP para información de trazabilidad
     * @return ResponseEntity con estructura Error estándar
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        // Construir mensaje con todos los campos inválidos
        StringBuilder mensajeCompleto = new StringBuilder();
        Map<String, String> erroresDetallados = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo;

            // Diferenciar entre errores de campo y errores de objeto (clase)
            if (error instanceof FieldError) {
                campo = ((FieldError) error).getField();
            } else {
                // Es un ObjectError (validación a nivel de clase como @ValidTimeRange)
                // En este caso, el error afecta a múltiples campos
                // Extraer los campos del mensaje o anotación
                ObjectError objectError = (ObjectError) error;

                // Para @ValidTimeRange, queremos mostrar el error en ambos campos de tiempo
                // Podemos extraerlo del código del error
                String code = objectError.getCode();
                if (code != null && code.contains("ValidTimeRange")) {
                    // Es un error de rango de tiempo, afecta a horaInicio y horaFin
                    campo = "horaInicio;horaFin"; // Usamos punto y coma para indicar múltiples campos
                } else {
                    // Fallback: usar el nombre del objeto
                    campo = objectError.getObjectName();
                }
            }

            // Procesar argumentos para convertir DefaultMessageSourceResolvable a valores
            // reales
            // Bean Validation pasa: [ConstraintDescriptorImpl, <valor_validacion>]
            // donde el primer elemento es el nombre del campo (que ignoramos) y los
            // siguientes
            // son los valores de la constraint (ej: min, max para @Size)
            Object[] args = error.getArguments();
            Object[] processedArgs = null;
            if (args != null && args.length > 1) {
                // Crear array con los argumentos reales, saltando el primer elemento
                // que es el DefaultMessageSourceResolvable con el nombre del campo
                processedArgs = new Object[args.length - 1];
                int targetIndex = 0;
                for (int i = 1; i < args.length; i++) {
                    processedArgs[targetIndex++] = args[i];
                }
            }

            String mensajeDeError = internacionalizarMensaje(error.getDefaultMessage(), processedArgs);

            // Si el campo contiene múltiples nombres separados por punto y coma,
            // agregar el error a cada uno
            String[] campos = campo.split(";");
            for (String c : campos) {
                c = c.trim();
                erroresDetallados.put(c, mensajeDeError);

                if (mensajeCompleto.length() > 0) {
                    mensajeCompleto.append("; ");
                }
                mensajeCompleto.append(c).append(": ").append(mensajeDeError);
            }
        });

        // Crear error siguiendo RFC 7807
        Error error = ErrorUtils.crearError(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(),
                mensajeCompleto.toString(),
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURL().toString(),
                req.getMethod());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private ResponseEntity<Error> createErrorResponse(CodigoError codigoError, HttpStatus httpStatus,
            HttpServletRequest req, String mensajeClave, Object... args) {
        String mensaje = internacionalizarMensaje(mensajeClave, args);

        Error error = ErrorUtils.crearError(
                codigoError.getCodigo(),
                mensaje,
                httpStatus.value(),
                req.getRequestURL().toString(),
                req.getMethod());

        return ResponseEntity.status(httpStatus).body(error);
    }

    private String internacionalizarMensaje(String claveMensaje, Object... args) {
        Object[] argumentos = (args == null || args.length == 0) ? new Object[] {} : args;
        return this.objFuenteMensajes.internacionalizarMensaje(claveMensaje, argumentos);
    }
}
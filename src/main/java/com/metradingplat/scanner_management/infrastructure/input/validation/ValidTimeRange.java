package com.metradingplat.scanner_management.infrastructure.input.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotación personalizada para validar que horaInicio < horaFin en un DTO.
 *
 * Esta anotación debe aplicarse a nivel de clase.
 *
 * Ejemplo de uso:
 * 
 * <pre>
 * {@code
 * @ValidTimeRange(startField = "horaInicio", endField = "horaFin", message = "validation.scanner.timeRange.invalid")
 * public class EscanerDTOPeticion {
 *     private LocalTime horaInicio;
 *     private LocalTime horaFin;
 * }
 * }
 * </pre>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeValidator.class)
@Documented
public @interface ValidTimeRange {

    String message() default "validation.scanner.timeRange.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Nombre del campo que contiene la hora de inicio
     */
    String startField() default "horaInicio";

    /**
     * Nombre del campo que contiene la hora de fin
     */
    String endField() default "horaFin";
}

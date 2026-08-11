package com.metradingplat.scanner_management.infrastructure.input.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalTime;

/**
 * Validador personalizado que verifica que un rango de tiempo sea válido.
 *
 * Valida que ambos campos (inicio y fin) no sean null. No exige que el
 * inicio sea anterior al fin -- un escaner de un mercado en otro huso
 * horario (ej. Tokio, 9pm-1:30am en referencia a Nueva York) cruza la
 * medianoche legitimamente, y el motor que evalua el escaner
 * (signal-processing-service, is_within_window) ya sabe manejar ese caso.
 *
 * Este validador se aplica a nivel de clase y accede a los campos
 * mediante reflexión usando Spring's BeanWrapper.
 */
public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, Object> {

    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(ValidTimeRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.startField();
        this.endFieldName = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull se encarga de validar null
        }

        try {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

            Object startValue = beanWrapper.getPropertyValue(startFieldName);
            Object endValue = beanWrapper.getPropertyValue(endFieldName);

            // Si alguno es null, dejamos que @NotNull maneje eso
            if (startValue == null || endValue == null) {
                return true;
            }

            // Validar que sean LocalTime -- se acepta inicio > fin (cruce de
            // medianoche) a proposito, ver comentario de la clase.
            return startValue instanceof LocalTime && endValue instanceof LocalTime;

        } catch (Exception e) {
            // Si hay error accediendo a las propiedades, consideramos inválido
            return false;
        }
    }
}

package com.metradingplat.scanner_management.infrastructure.input.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalTime;

/**
 * Validador personalizado que verifica que un rango de tiempo sea válido.
 *
 * Valida que:
 * - Ambos campos (inicio y fin) no sean null
 * - La hora de inicio sea anterior a la hora de fin
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

            // Validar que sean LocalTime
            if (!(startValue instanceof LocalTime) || !(endValue instanceof LocalTime)) {
                return false;
            }

            LocalTime startTime = (LocalTime) startValue;
            LocalTime endTime = (LocalTime) endValue;

            // La hora de inicio debe ser anterior a la hora de fin
            return startTime.isBefore(endTime);

        } catch (Exception e) {
            // Si hay error accediendo a las propiedades, consideramos inválido
            return false;
        }
    }
}

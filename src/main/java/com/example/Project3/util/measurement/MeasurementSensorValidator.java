package com.example.Project3.util.measurement;

import com.example.Project3.models.Measurement;
import com.example.Project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementSensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementSensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor() == null || sensorService.findByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor","","Sensor not found");
        }
     }
}

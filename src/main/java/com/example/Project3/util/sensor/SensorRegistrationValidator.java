package com.example.Project3.util.sensor;

import com.example.Project3.dto.SensorDTO;
import com.example.Project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorRegistrationValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorRegistrationValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        if (sensorService.findByName(sensorDTO.getName()).isPresent()){
            errors.rejectValue("name","","This name is already in use");
        }
    }
}

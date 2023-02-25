package com.example.Project3.controllers;

import com.example.Project3.dto.SensorDTO;
import com.example.Project3.dto.SensorResponse;
import com.example.Project3.models.Sensor;
import com.example.Project3.services.SensorService;
import com.example.Project3.util.ErrorResponse;
import com.example.Project3.util.ErrorUtil;
import com.example.Project3.util.sensor.SensorNotCreatedException;
import com.example.Project3.util.sensor.SensorRegistrationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorRegistrationValidator sensorRegistrationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService,
                            SensorRegistrationValidator sensorRegistrationValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorRegistrationValidator = sensorRegistrationValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public SensorResponse findAll() {
        return new SensorResponse(sensorService.findAll().stream()
                .map(this::convertToSensorDTO).collect(Collectors.toList()));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        sensorRegistrationValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new SensorNotCreatedException(ErrorUtil.errorsToString(bindingResult));
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(SensorNotCreatedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}

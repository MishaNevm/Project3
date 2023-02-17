package com.example.Project3.controllers;

import com.example.Project3.dto.MeasurementDTO;
import com.example.Project3.models.Measurement;
import com.example.Project3.services.MeasurementService;
import com.example.Project3.util.ErrorResponse;
import com.example.Project3.util.measurement.MeasurementNotCreatedException;
import com.example.Project3.util.measurement.MeasurementNotFoundException;
import com.example.Project3.util.measurement.MeasurementSensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementSensorValidator measurementSensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementSensorValidator measurementSensorValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementSensorValidator = measurementSensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> findAll() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).toList();
    }

    @GetMapping("/rainyDaysCount")
    public List<MeasurementDTO> findByRainyDays() {
        return measurementService.findByRain().stream().map(this::convertToMeasurementDTO).toList();

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(MeasurementNotFoundException e) {
        ErrorResponse measurementErrorResponse =
                new ErrorResponse("Measurements not found", System.currentTimeMillis());
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementSensorValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(MeasurementNotCreatedException e) {
        ErrorResponse measurementErrorResponse =
                new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}

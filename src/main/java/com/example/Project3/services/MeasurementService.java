package com.example.Project3.services;

import com.example.Project3.models.Measurement;
import com.example.Project3.models.Sensor;
import com.example.Project3.repositoryes.MeasurementRepository;
import com.example.Project3.repositoryes.SensorRepository;
import com.example.Project3.util.measurement.MeasurementNotFoundException;
import com.example.Project3.util.sensor.SensorNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public List<Measurement> findAll() {
        List<Measurement> measurements = measurementRepository.findAll();
        if (measurements.isEmpty()) throw new MeasurementNotFoundException("Measurement not found");
        return measurements;
    }

    @Transactional
    public List<Measurement> findByRain() {
        List<Measurement> rainMeasurements = measurementRepository.findByRain(true);
        if (rainMeasurements.isEmpty()) throw new MeasurementNotFoundException("Rain measurement not found");
        return rainMeasurements;
    }

    @Transactional
    public void save(Measurement measurement) {
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName())
                .orElseThrow(SensorNotFoundException::new);
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());
        sensor.setLastAddMeasurements(LocalDateTime.now());
        measurementRepository.save(measurement);
    }
}

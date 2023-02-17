package com.example.Project3.services;

import com.example.Project3.models.Sensor;
import com.example.Project3.repositoryes.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Sensor> findByName (String name) {
        return sensorRepository.findByName(name);
    }

    public void save (Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensorRepository.save(sensor);
    }

}

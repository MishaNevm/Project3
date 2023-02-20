package com.example.Project3.util.measurement;

import com.example.Project3.models.Measurement;

public class MeasurementNotFoundException extends RuntimeException{
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}

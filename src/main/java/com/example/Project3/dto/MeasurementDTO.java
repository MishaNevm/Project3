package com.example.Project3.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull(message = "Temperature should be not null")
    @Min(value = -100, message = "Temperature should be bigger than -100")
    @Max(value = 100, message = "Temperature should be less than -100")
    private double temperature;

    @NotNull(message = "Rain value should be not empty")
    private boolean rain;

    @NotNull(message = "Sensor should be not null")
    private SensorDTO sensor;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

}

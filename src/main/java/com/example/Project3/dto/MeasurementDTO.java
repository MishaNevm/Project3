package com.example.Project3.dto;

import javax.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull(message = "Temperature should be not null")
    private float temperature;

    @NotNull(message = "Rain value should be not empty")
    private boolean rain;

    @NotNull(message = "Sensor should be not null")
    private SensorDTO sensor;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
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

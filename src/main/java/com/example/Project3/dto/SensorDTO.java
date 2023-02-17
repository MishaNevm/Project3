package com.example.Project3.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 2, max = 30, message = "Sensor name should be in range from 2 to 30")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

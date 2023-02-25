package com.example.Project3.dto;

import java.util.List;

public class SensorResponse {
    private List<SensorDTO> sensorDTOList;

    public SensorResponse(List<SensorDTO> sensorDTOList) {
        this.sensorDTOList = sensorDTOList;
    }

    public SensorResponse() {
    }

    public List<SensorDTO> getSensorDTOList() {
        return sensorDTOList;
    }

    public void setSensorDTOList(List<SensorDTO> sensorDTOList) {
        this.sensorDTOList = sensorDTOList;
    }
}

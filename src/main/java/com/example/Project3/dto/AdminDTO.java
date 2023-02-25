package com.example.Project3.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AdminDTO {

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 30, message = "Name should be in range from 2 to 30")
    private String name;

    @NotEmpty(message = "Password should be not empty")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

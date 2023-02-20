package com.example.Project3.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorUtil {
    public static String errorsToString (BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(" - ")
                    .append(fieldError.getDefaultMessage()).append(";");
        }
        return errorMessage.toString();
    }
}

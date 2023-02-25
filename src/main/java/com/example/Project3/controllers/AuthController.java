package com.example.Project3.controllers;

import com.example.Project3.dto.AdminDTO;
import com.example.Project3.models.Admin;
import com.example.Project3.security.JWTUtil;
import com.example.Project3.services.AdminService;
import com.example.Project3.util.ErrorResponse;
import com.example.Project3.util.ErrorUtil;
import com.example.Project3.util.admin.AdminNotCreatedException;
import com.example.Project3.util.admin.AdminValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AdminService adminService;
    private final AdminValidator adminValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AdminService adminService, AdminValidator adminValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.adminService = adminService;
        this.adminValidator = adminValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AdminDTO adminDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(adminDTO.getName(), adminDTO.getPassword());
        authenticationManager.authenticate(authInputToken);
        String token = jwtUtil.generateToken(adminDTO.getName());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/registration")
    public Map<String, String> registration(@RequestBody @Valid AdminDTO adminDTO, BindingResult bindingResult) {
        Admin admin = convertToAdmin(adminDTO);
        adminValidator.validate(admin, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new AdminNotCreatedException(ErrorUtil.errorsToString(bindingResult));
        }
        adminService.registration(admin);
        String token = jwtUtil.generateToken(admin.getName());
        return Map.of("jwt-token", token);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(AdminNotCreatedException adminNotCreatedException) {
        ErrorResponse errorResponse = new ErrorResponse(adminNotCreatedException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public Admin convertToAdmin(AdminDTO adminDTO) {
        return modelMapper.map(adminDTO, Admin.class);
    }
}

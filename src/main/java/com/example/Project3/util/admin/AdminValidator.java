package com.example.Project3.util.admin;

import com.example.Project3.models.Admin;
import com.example.Project3.services.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AdminValidator implements Validator {

    private final AdminService adminService;

    public AdminValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Admin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Admin admin = (Admin) target;
        if (adminService.findByName(admin.getName()).isPresent())
            errors.rejectValue("name","","This name is already in use");
    }
}

package com.example.Project3.services;

import com.example.Project3.models.Admin;
import com.example.Project3.repositoryes.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    public void registration (Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    public Optional<Admin> findByName (String name) {
        return adminRepository.findByName(name);
    }

}

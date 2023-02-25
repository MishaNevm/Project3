package com.example.Project3.services;

import com.example.Project3.models.Admin;
import com.example.Project3.repositoryes.AdminRepository;
import com.example.Project3.security.AdminDetails;
import com.example.Project3.util.admin.AdminNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByName(username);
        if (adminOptional.isEmpty()) throw new AdminNotFoundException();
        return new AdminDetails(adminOptional.get());
    }
}

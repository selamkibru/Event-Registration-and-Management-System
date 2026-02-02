package com.eventmanagement.service;

import com.eventmanagement.model.AdminAccount;
import com.eventmanagement.repository.AdminRepository;

public class AdminAuthService {

    private final AdminRepository repo;

    public AdminAuthService(AdminRepository repo) {
        this.repo = repo;
    }

    public void register(String fullName, String username, String email,
                         String phone, String password) {

        if (repo.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        repo.save(new AdminAccount(
                "A" + System.currentTimeMillis(),
                fullName,
                username,
                email,
                phone,
                password
        ));
    }


    public AdminAccount login(String username, String password) {

        AdminAccount admin = repo.findByUsername(username);

        if (admin == null) {
            throw new IllegalArgumentException("This username is not registered");
        }

        if (!admin.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password. Please try again");
        }

        return admin;
    }
}


package com.eventmanagement.repository;

import com.eventmanagement.model.AdminAccount;

public interface AdminRepository extends Repository<AdminAccount> {
    AdminAccount findByUsername(String username);
}


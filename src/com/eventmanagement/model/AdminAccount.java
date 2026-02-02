package com.eventmanagement.model;

public class AdminAccount {

    private String adminId;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String password;

    public AdminAccount(String adminId, String fullName,
                        String username, String email,
                        String phone, String password) {

        this.adminId = adminId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getAdminId() { return adminId; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
}


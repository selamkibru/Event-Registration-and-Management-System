package com.eventmanagement.model;

public class Admin extends User {

    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    public void showDashboard() {
        System.out.println("Admin Dashboard: manage events and users");
    }

    public void addEvent() {
        System.out.println("Admin adds an event");
    }

    public void removeEvent() {
        System.out.println("Admin removes an event");
    }
}


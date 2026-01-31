package com.eventmanagement.model;

public class Attendee extends User {

    public Attendee(String id, String name, String email) {
        super(id, name, email);
    }

    public void showDashboard() {
        System.out.println("Attendee Dashboard: view and register for events");
    }

    public void registerForEvent(String eventName) {
        System.out.println("Attendee registered for " + eventName);
    }
}


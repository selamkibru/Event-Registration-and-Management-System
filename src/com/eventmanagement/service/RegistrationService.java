package com.eventmanagement.service;

import com.eventmanagement.model.Registration;
import com.eventmanagement.repository.RegistrationRepository;

import java.util.List;

public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    // Register attendee
    public void register(Registration registration) {
        if (registration.getAttendeeId().isEmpty() || registration.getEventId().isEmpty()) {
            throw new IllegalArgumentException("Invalid registration data");
        }
        registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(String id) {
        Registration r = registrationRepository.findById(id);
        if (r == null) {
            throw new RuntimeException("Registration not found");
        }
        return r;
    }

    public void cancelRegistration(String id) {
        registrationRepository.delete(id);
    }
}
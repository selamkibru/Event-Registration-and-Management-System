package com.eventmanagement.service;

import com.eventmanagement.model.Registration;
import com.eventmanagement.repository.RegistrationRepository;
import com.eventmanagement.util.InputValidator;

import java.util.List;

public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    // Register attendee
    public void register(Registration registration) {
        InputValidator.validateNonEmpty("Attendee ID", registration.getAttendeeId());
        InputValidator.validateNonEmpty("Event ID", registration.getEventId());

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
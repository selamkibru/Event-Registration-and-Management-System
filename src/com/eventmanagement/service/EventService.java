package com.eventmanagement.service;

import com.eventmanagement.model.Event;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.util.InputValidator;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Add or update event
    public void saveEvent(Event event) {
        InputValidator.validateNonEmpty("Event name", event.getName());
        InputValidator.validateNonEmpty("Event date", event.getDate());
        InputValidator.validatePositive("Event capacity", event.getCapacity());

        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        Event e = eventRepository.findById(id);
        if (e == null) {
            throw new RuntimeException("Event not found");
        }
        return e;
    }

    public void deleteEvent(String id) {
        eventRepository.delete(id);
    }
}


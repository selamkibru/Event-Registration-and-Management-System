package com.eventmanagement.service;

import com.eventmanagement.model.Event;
import com.eventmanagement.repository.EventRepository;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Add or update event
    public void saveEvent(Event event) {
        if (event.getName().isEmpty() || event.getCapacity() <= 0) {
            throw new IllegalArgumentException("Invalid event data");
        }
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


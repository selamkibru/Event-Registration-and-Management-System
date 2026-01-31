package com.eventmanagement.model;

public class Event {
    private String eventId;
    private String name;
    private String date; // Keep simple as String for now
    private int capacity;

    public Event(String eventId, String name, String date, int capacity) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
    }

    public String getEventId() {
        return eventId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

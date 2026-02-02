package com.eventmanagement.model;

public class Event {

    private String eventId;
    private String name;
    private String date;
    private int capacity;
    private String createdByAdmin; // 👈 NEW

    public Event(String eventId, String name,
                 String date, int capacity,
                 String createdByAdmin) {

        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.createdByAdmin = createdByAdmin;
    }

    public String getEventId() { return eventId; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public int getCapacity() { return capacity; }
    public String getCreatedByAdmin() { return createdByAdmin; }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

package com.eventmanagement.model;

public class Registration {
    private String registrationId;
    private String eventId;
    private String attendeeId;

    public Registration(String registrationId, String eventId, String attendeeId) {
        this.registrationId = registrationId;
        this.eventId = eventId;
        this.attendeeId = attendeeId;
    }

    public String getRegistrationId() {
        return registrationId;
    }
    public String getEventId() {
        return eventId;
    }
    public String getAttendeeId() {
        return attendeeId;
    }
}

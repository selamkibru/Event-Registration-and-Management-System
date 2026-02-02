package com.eventmanagement.model;

public class Registration {

    private String registrationId;
    private String eventId;
    private String attendeeName;
    private String email;
    private String phone;

    public Registration(String registrationId, String eventId,
                        String attendeeName, String email, String phone) {

        this.registrationId = registrationId;
        this.eventId = eventId;
        this.attendeeName = attendeeName;
        this.email = email;
        this.phone = phone;
    }

    public String getRegistrationId() { return registrationId; }
    public String getEventId() { return eventId; }
    public String getAttendeeName() { return attendeeName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

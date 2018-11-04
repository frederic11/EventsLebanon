package com.frederictech.eventslebanontest001;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


/**
 * Created by Frederic on 23-04-2017.
 */

public class Event {

    private int eventId;
    private String eventName;
    private String eventDescription;
    private enum eventTypeEnum {ACADEMIC, PARTY, BIRTHDAY, FOODFEST, OTHER}
    private String eventType = enumEventTypeClass.getTypeOfEvent(eventTypeEnum.ACADEMIC);
    private LatLng eventLocation = new LatLng(0, 0);
    private boolean isFoodAvailable;
    private boolean isAlcoholAvailable;
    private int numberOfAttendees;
    private boolean isFull;
    private int maxNumberOfAttendees;
    private String eventDate;
    private String eventExpiryDate;
    private boolean isDeleted;
    private boolean hasReservations;
    private boolean hasTickets;
    private String organizerId;


    public Event() {
        eventId = -1;
        eventName = "Default";
        eventDescription = "Default";
        eventType = "ACADEMIC";
        eventLocation = new LatLng(0, 0);
        isFoodAvailable = false;
        isAlcoholAvailable = false;
        numberOfAttendees = 0;
        isFull = false;
        maxNumberOfAttendees = 999999999;
        eventDate = (new Date(2018, 1, 1)).toString();
        eventExpiryDate = (new Date(2019, 1, 1)).toString();
        isDeleted = false;
        hasReservations = false;
        hasTickets = false;
        organizerId = "112106928278476143898"; //admin ID
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventExpiryDate() {
        return eventExpiryDate;
    }

    public void setEventExpiryDate(String eventExpiryDate) {
        this.eventExpiryDate = eventExpiryDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LatLng getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(LatLng eventLocation) {
        this.eventLocation = eventLocation;
    }

    public boolean isFoodAvailable() {
        return isFoodAvailable;
    }

    public void setFoodAvailable(boolean foodAvailable) {
        isFoodAvailable = foodAvailable;
    }

    public boolean isAlcoholAvailable() {
        return isAlcoholAvailable;
    }

    public void setAlcoholAvailable(boolean alcoholAvailable) {
        isAlcoholAvailable = alcoholAvailable;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public int getMaxNumberOfAttendees() {
        return maxNumberOfAttendees;
    }

    public void setMaxNumberOfAttendees(int maxNumberOfAttendees) {
        this.maxNumberOfAttendees = maxNumberOfAttendees;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public static final class enumEventTypeClass {
        eventTypeEnum eventType;

        public enumEventTypeClass(eventTypeEnum eventType) {
            this.eventType = eventType;
        }

        static String getTypeOfEvent(eventTypeEnum e)
        {
            return e.toString();
        }


    }

    public boolean isHasReservations() {
        return hasReservations;
    }

    public void setHasReservations(boolean hasReservations) {
        this.hasReservations = hasReservations;
    }

    public boolean isHasTickets() {
        return hasTickets;
    }

    public void setHasTickets(boolean hasTickets) {
        this.hasTickets = hasTickets;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }
}

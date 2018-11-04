package com.frederictech.eventslebanontest001;

import java.util.Date;

/**
 * Created by Frederic on 05-05-2017.
 */

public class EventTicket {

    private int ticketId;
    private String ticketTitle;
    private String ticketDescription;
    private double price;
    private Date ticketCreationDate;
    private String userCreatorId;
    private String eventAssociatedId;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public Date getTicketCreationDate() {
        return ticketCreationDate;
    }

    public void setTicketCreationDate(Date ticketCreationDate) {
        this.ticketCreationDate = ticketCreationDate;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public String getEventAssociatedId() {
        return eventAssociatedId;
    }

    public void setEventAssociatedId(String eventAssociatedId) {
        this.eventAssociatedId = eventAssociatedId;
    }
}

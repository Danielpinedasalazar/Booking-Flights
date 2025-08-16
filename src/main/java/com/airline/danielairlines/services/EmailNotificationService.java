package com.airline.danielairlines.services;

import com.airline.danielairlines.entities.Booking;
import com.airline.danielairlines.entities.User;

public interface EmailNotificationService {

    void sendBooingTicketsEmail(Booking booking);

    void sendWelcomeEmail(User user);
}

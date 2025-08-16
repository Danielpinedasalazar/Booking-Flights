package com.airline.danielairlines.services.impl;

import com.airline.danielairlines.entities.Booking;
import com.airline.danielairlines.entities.EmailNotification;
import com.airline.danielairlines.entities.User;
import com.airline.danielairlines.repo.EmailNotificationRepo;
import com.airline.danielairlines.services.EmailNotificationService;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final EmailNotificationRepo emailNotificationRepo;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${frontendLoginUrl}")
    private String frontendLoginUrl;

    @Value("${viewBookingUrl}")
    private String viewBookingUrl;

    @Override
    @Transactional
    @Async
    public void sendBooingTicketsEmail(Booking booking) {
        log.info("Inside sendBooingTicketsEmail()");
        String recipientEmail = booking.getUser().getEmail();
        String subject = "Your Flight Booking";
        String templateName = "booking_ticket";

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", booking.getUser().getName());
        templateVariables.put("bookingReference", booking.getBookingReference());
        templateVariables.put("flightNumber", booking.getFlight().getFlightNumber());
        templateVariables.put("departureAirportIataCode", booking.getFlight().getDepartureAirport().getIataCode());
        templateVariables.put("departureAirportName", booking.getFlight().getDepartureAirport().getName());
        templateVariables.put("departureAirportCity", booking.getFlight().getDepartureAirport().getCity());
        templateVariables.put("departureTime", booking.getFlight().getDepartureTime());
        templateVariables.put("arrivalAirportIataCode", booking.getFlight().getArrivalAirport().getIataCode());
        templateVariables.put("arrivalAirportName", booking.getFlight().getArrivalAirport().getName());
        templateVariables.put("arrivalAirportCity", booking.getFlight().getArrivalAirport().getCity());
        templateVariables.put("arrivalTime", booking.getFlight().getArrivalTime());
        templateVariables.put("basePrice", booking.getFlight().getBasePrice());
        templateVariables.put("passengers", booking.getPassengers());
        templateVariables.put("viewBookingUrl", viewBookingUrl);

        //Render the template content
        Context context = new Context();
        templateVariables.forEach(context::setVariable);
        String emailBody = templateEngine.process(templateName, context);

        //Send the actual email with the template
        sendMailOut(recipientEmail, subject, emailBody,true, booking);

    }

    @Override
    @Transactional
    @Async
    public void sendWelcomeEmail(User user) {
        log.info("Sending welcome email to user: {}", user.getEmail());
        String recipientEmail = user.getName();
        String subject = "Welcome to Daniel Airlines";
        String templateName = "welcome_user";

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", user.getName());
        templateVariables.put("frontendLoginUrl", frontendLoginUrl);

        Context context = new Context();
        templateVariables.forEach(context::setVariable);
        String emailBody = templateEngine.process(templateName, context);

        sendMailOut(recipientEmail, subject, emailBody, true, null);
    }

    private void sendMailOut(String recipientEmail, String subject, String body, boolean isHtml, Booking booking) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            log.info("About to send email...");
            javaMailSender.send(mimeMessage);

            log.info("Email sent out");

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        //Save to the notification database table
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipientEmail(recipientEmail);
        emailNotification.setSubject(subject);
        emailNotification.setBody(body);
        emailNotification.setHtml(isHtml);
        emailNotification.setSendAt(LocalDateTime.now());
        emailNotification.setBooking(booking);

        emailNotificationRepo.save(emailNotification);

    }
}

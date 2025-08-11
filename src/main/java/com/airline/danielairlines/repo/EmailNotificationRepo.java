package com.airline.danielairlines.repo;

import com.airline.danielairlines.entities.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepo extends JpaRepository<EmailNotification, Long> {
}

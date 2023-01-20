package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Notification;
import com.kalata.spring.login.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public List<Notification> lister() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Long id) {
        return null;
    }

    @Override
    public Notification ajout(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public String delete(Long id) {
        notificationRepository.deleteById(id);
        return"Notification supprimé";
    }
}

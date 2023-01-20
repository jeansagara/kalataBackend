package com.kalata.spring.login.security.services;




import com.kalata.spring.login.models.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> lister();
    Notification findById(Long id);
    Notification ajout(Notification notification);
    String delete(Long id);
}

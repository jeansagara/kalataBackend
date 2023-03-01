package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Administration;
import com.kalata.spring.login.models.Candidat;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.List;

public interface AdministrationService {
    List<Administration> lister();
    Administration findById(Long id);
    Administration ajout(Administration administration);
    Administration modifier(Long id, Administration administration);
    String delete(Long id);
    Administration save(Administration administration);


/*    //Status
   @Scheduled(fixedRate = 86400000)
    String Heurdate();
   */
}

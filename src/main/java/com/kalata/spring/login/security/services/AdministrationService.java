package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Administration;
import com.kalata.spring.login.models.Candidat;


import java.util.List;

public interface AdministrationService {
    List<Administration> lister();
    Administration findById(Long id);
    Administration ajout(Administration administration);
    Administration modifier(Long id, Administration administration);
    String delete(Long id);
    Administration save(Administration administration);



}

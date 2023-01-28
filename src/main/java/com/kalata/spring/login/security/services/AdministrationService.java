package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Administration;


import java.util.List;

public interface AdministrationService {
    List<Administration> lister();
    Administration findById(Long id);
    Administration ajout(Administration administration);
    Administration modifier(Long id, Administration administration);
    String delete(Long id);
    Administration save(Administration administration);


    // toujours empecher de... deux fois
    boolean existByTitre(String titre);
}

package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.models.Region;
import com.kalata.spring.login.payload.response.MessageResponse;

import java.util.List;

public interface PaysService {


    MessageResponse creer(Pays pays);

    List<Pays> lire();
    Pays modifier(Long id, Pays pays);
    String supprimer(Long id);
}

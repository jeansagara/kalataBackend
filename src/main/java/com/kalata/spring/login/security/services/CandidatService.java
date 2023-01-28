package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Candidat;


import java.util.List;


public interface CandidatService {
    List<Candidat> lister();
    Candidat findById(Long id);
    Candidat ajout(Candidat candidat);
    Candidat modifier(Long id, Candidat candidat);
    String delete(Long id);
}


package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.models.Vote;

import java.util.List;

public interface Type_voteService {
    Type_vote save(Type_vote type_vote);
    String supprimer (Long id);
    Type_vote findById(Long id);
    List<Type_vote> afficher();
    //pour empecher de.... deux fois
    boolean existByType_vote(String nom);

}

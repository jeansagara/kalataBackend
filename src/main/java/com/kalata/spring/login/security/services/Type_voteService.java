package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Type_vote;

public interface Type_voteService {
    Type_vote save(Type_vote type_vote);
    //pour empecher de.... deux fois
    boolean existByType_vote(String nom);

}

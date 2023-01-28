package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.Type_voteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Type_voteImplement implements Type_voteService{
    @Autowired
    Type_voteRepository type_voteRepository;
    @Override
    public Type_vote save(Type_vote type_vote) {
        return type_voteRepository.save(type_vote);
    }

    // toujours empecher de... deux fois
    @Override
    public boolean existByType_vote(String nom) {
        return type_voteRepository.existsType_voteByNom(nom);
    }
}

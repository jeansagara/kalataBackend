package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.Type_voteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Type_voteImplement implements Type_voteService{
    @Autowired
    Type_voteRepository type_voteRepository;
    @Override
    public Type_vote save(Type_vote type_vote) {
        return type_voteRepository.save(type_vote);
    }

    @Override
    public String supprimer(Long id) {
        this.type_voteRepository.deleteById(id);
        return "vote supprimer avec succes";
    }

    @Override
    public Type_vote findById(Long id) {
        return type_voteRepository.findById(id).get();
    }

    @Override
    public List<Type_vote> afficher() {
        return type_voteRepository.findAll();
    }

    // toujours empecher de... deux fois
    @Override
    public boolean existByType_vote(String nom) {
        return type_voteRepository.existsType_voteByNom(nom);
    }
}

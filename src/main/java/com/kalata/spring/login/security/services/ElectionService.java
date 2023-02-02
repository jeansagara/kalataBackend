package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Election;

import java.util.List;

public interface ElectionService {

    List<Election> findAll();
    Election findById(Long id);
    Election getById(Long id);
    Election save(Election election);
    String delete(Long id);
    List<Election> getElectionsByTypeVoteId(Long typeVoteId);

}

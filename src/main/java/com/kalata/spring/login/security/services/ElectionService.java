package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Election;

import java.util.List;

public interface ElectionService {

    List<Election> findAll();
    Election findById(Long id);
    Election save(Election election);
    String delete(Long id);
}

package com.kalata.spring.login.controllers;

import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.repository.UtilisateursRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin(value = {"http://localhost:8100","http://localhost:4200"}, maxAge = 3600,allowCredentials = "true")

@ToString
@RequestMapping("/api")
public class UtilisateurController {
    @Autowired
    UtilisateursRepository utilisateursRepository;
    @GetMapping("/utilisateur/list")
    public List<Utilisateurs> lister() {
        return utilisateursRepository.findAll();
    }
}

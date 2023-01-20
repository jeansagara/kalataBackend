package com.kalata.spring.login.controllers;

import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.security.services.Type_voteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/typevote")
public class Type_voteController {
    @Autowired
    private Type_voteService type_voteService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Ajouter")
    public Object save(@RequestBody Type_vote type_vote){
        type_voteService.save(type_vote);
        return "Ajouter avec succèss";
    }
}

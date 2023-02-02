package com.kalata.spring.login.controllers;

import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.ElectionRepository;
import com.kalata.spring.login.repository.Type_voteRepository;
import com.kalata.spring.login.security.services.Type_voteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/typevote")
public class Type_voteController {

    @Autowired
    private Type_voteService type_voteService;

    @Autowired
    Type_voteRepository type_voteRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter")
    public Object save(@RequestBody Type_vote type_vote){
        //tjrs empecher de... deux fois
        if (type_voteService.existByType_vote(type_vote.getNom())){
            return "Ce meme nom existe deja";
        }
        type_voteService.save(type_vote);
        return "Vote ajouter avec succèss";

    }

    @GetMapping("/afficher")
    public List<Type_vote> list() {
        return type_voteService.afficher();
    }

    @GetMapping("/afficher/{id}")
    public ResponseEntity<Type_vote> findById(@PathVariable Long id) {
        Type_vote type_vote = type_voteService.findById(id);
        if (type_vote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(type_vote);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/supprimer/{Id}")
    public String Supprimer(@PathVariable Long Id) {
        return type_voteService.supprimer(Id);
    }
}

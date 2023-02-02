package com.kalata.spring.login.controllers;


import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.models.Vote;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.security.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600,allowCredentials = "true")

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    VoteService voteService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter")
    public MessageResponse creer(@RequestBody Vote vote) {
        return voteService.creer(vote);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/afficher")
    public List<Vote> list() {
        return voteService.afficher();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/supprimer/{Id}")
    public String Supprimer(@PathVariable Long Id) {
        return voteService.supprimer(Id);
    }

    // VOTEE POUR UN CANDIDAT DANS UNE ELECTION
    @PreAuthorize("hasRole('ELECTEUR')")
    @PostMapping("/creervote/{id_candidat}/{idelection}/{idutilisateur}")
    public MessageResponse creerVote(
            @PathVariable Long id_candidat,
            @PathVariable Long idelection,
            @PathVariable("idutilisateur") Utilisateurs idutilisateur
    ) {
        Vote v = new Vote();
        v.setUtilisateurs(idutilisateur);
        return voteService.creerVote(id_candidat, idelection, idutilisateur);
    }

    // VOTEE POUR UN PROJET DE LOI SELON LES VOIX (Pour, Contre, Contre)
    @PostMapping("/voteprojets/{idAdministration}/{idutilisateur}/{vote}")
    public MessageResponse VoteAdministration(     @PathVariable Long idAdministration,
                                                   @PathVariable("idutilisateur") Utilisateurs idutilisateur,
                                                   @PathVariable int vote){
        Vote vp = new Vote();
        vp.setUtilisateurs(idutilisateur);
        return voteService.voteprojetloie(idAdministration,idutilisateur,vote);
    }
















    }

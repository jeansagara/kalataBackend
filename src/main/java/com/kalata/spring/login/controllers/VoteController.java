package com.kalata.spring.login.controllers;


import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.models.Vote;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.security.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = {"http://localhost:8100","http://localhost:4200"}, maxAge = 3600,allowCredentials = "true")

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

   /* @PreAuthorize("hasRole('ADMIN')")*/
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
  //@PreAuthorize("hasRole('ELECTEUR')")
        @PostMapping("/creervote/{idutilisateur}/{idelection}/{id_candidat}/{latitude}/{longitude}")
    public MessageResponse creerVote(
            @PathVariable Long id_candidat,
            @PathVariable Long idelection,
            @PathVariable("idutilisateur") Utilisateurs idutilisateur,
            @PathVariable("latitude") Double latitude,
            @PathVariable("longitude") Double longitude) {
        Vote v = new Vote();
        v.setUtilisateurs(idutilisateur);
        v.setLatitude(latitude);
        v.setLongitude(longitude);

        return voteService.creerVote(id_candidat, idelection, idutilisateur,latitude,longitude);
    }




    // VOTEE POUR UN PROJET DE LOI SELON LES VOIX (Pour, Contre, Contre)
    @PostMapping("/voteprojets/{idAdministration}/{idutilisateur}/{vote}/{latitude}/{longitude}")
    public MessageResponse VoteAdministration(     @PathVariable("idAdministration") Long idAdministration,
                                                   @PathVariable("idutilisateur") Utilisateurs idutilisateur,
                                                   @PathVariable int vote,
                                                   @PathVariable("latitude") Double latitude,
                                                   @PathVariable("longitude") Double longitude){

        if (voteService.aDejaVote(idAdministration, idutilisateur)) {
            return new MessageResponse("Désolé, vous avez déjà voté pour ce projet de loi.");
        }
        Vote vp = new Vote();
        vp.setLatitude(latitude);
        vp.setLongitude(longitude);
        vp.setUtilisateurs(idutilisateur);
        return voteService.voteprojetloie(idAdministration,idutilisateur,vote,latitude,longitude);
    }





/*    @PostMapping("/voteprojets/{idAdministration}/{idutilisateur}/{vote}")
    public MessageResponse VoteAdministration(     @PathVariable Long idAdministration,
                                                   @PathVariable("idutilisateur") Utilisateurs idutilisateur,
                                                   @PathVariable int vote){
        Vote vp = new Vote();
        vp.setUtilisateurs(idutilisateur);
        return voteService.voteprojetloie(idAdministration,idutilisateur,vote);
    }*/
/*@PostMapping("/voteprojets/{idAdministration}/{idutilisateur}/{vote}/{latitude}/{longitude}")

public MessageResponse VoteAdministrationn(@PathVariable("idAdministration") Long idAdministration,
                                          @PathVariable("idutilisateur") Utilisateurs idutilisateur,
                                          @PathVariable int vote,
                                          @PathVariable("latitude") Double latitude,
                                          @PathVariable("longitude") Double longitude) {
    // Vérifier si l'utilisateur a déjà voté pour ce projet de loi
   if (voteService.aDejaVote(idAdministration, idutilisateur)) {
        return new MessageResponse("Désolé, vous avez déjà voté pour ce projet de loi.");
    }

    // Créer un nouveau vote pour ce projet de loi
    Vote vp = new Vote();
    vp.setLatitude(latitude);
    vp.setLongitude(longitude);
    vp.setUtilisateurs(idutilisateur);

    // Enregistrer le vote dans la base de données
    voteService.voteprojetloie(idAdministration, idutilisateur, vote, latitude, longitude);

    return new MessageResponse("Votre vote a été enregistré avec succès.");
}*/















    }

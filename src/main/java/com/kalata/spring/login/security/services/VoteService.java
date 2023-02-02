package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.models.Vote;
import com.kalata.spring.login.payload.response.MessageResponse;

import java.util.List;

public interface VoteService  {

    MessageResponse creer(Vote vote);
    List<Vote> afficher();
    String supprimer (Long id);
    Vote id_vote(Long id);
    MessageResponse creerVote(Long id_candidat, Long idelection, Utilisateurs idutilisateur);
    MessageResponse voteprojetloie(Long idAdministration, Utilisateurs idutilisateur, int vote);
}

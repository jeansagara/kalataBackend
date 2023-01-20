package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.models.Vote;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.CandidatRepository;
import com.kalata.spring.login.repository.ElectionRepository;
import com.kalata.spring.login.repository.UtilisateursRepository;
import com.kalata.spring.login.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService{

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UtilisateursRepository utilisateursRepository;

    @Autowired
    CandidatRepository candidatRepository;

    @Autowired
    ElectionRepository electionRepository;

    @Override
    public MessageResponse creer(Vote vote) {


        if(voteRepository.findById(vote.getIdvote()) != null){

            MessageResponse message = new MessageResponse("Ce vote existe déja");
            return message;
        }
        else{
            voteRepository.save(vote);
            MessageResponse message = new MessageResponse("Vote ajouter avec succès");
            return message;
        }


    }



    @Override
    public List<Vote> afficher() {
        return voteRepository.findAll();
    }

    @Override
    public String supprimer(Long id) {
        this.voteRepository.findById(id);
        return "vote supprimer avec succes";
    }

    @Override
    public Vote id_vote(Long id) {
        return voteRepository.findById(id).get();
    }

    @Override
    public MessageResponse creerVote(Long id_candidat, Long idelection, Utilisateurs idutilisateur) {
        // Vote vote = new Vote();
       /* Utilisateurs utils = utilisateursRepository.findByIdutilisateur(id);
        vote.setUtilisateurs(utils);
        */
        Vote vote = new Vote();
        Candidat candidat = candidatRepository.findByIdcandidat(id_candidat);
        vote.setCandidat(candidat);
        Election election = electionRepository.findByIdelection(idelection);
        vote.setElection(new Election(idelection));
        vote.setUtilisateurs(idutilisateur);
        vote.setDate(new Date());


        List<Vote> listedevote = voteRepository.findByElection(vote.getElection());
//        Utilisateurs utilisateurs = null;
        Optional<Vote> use = voteRepository.findByUtilisateurs(idutilisateur);
        System.out.println("zreegfgrthgttggtg"+vote);
        for (Vote utilisateurvote : listedevote){

            System.out.println("zreegfgrthgttggtg"+vote.getCandidat());

          /*  if (utilisateurvote.getUtilisateurs().equals(utilisateurs)) utilisateurs = vote.getUtilisateurs();{
             // if(utilisateursRepository.findById(id) == null){
                MessageResponse message = new MessageResponse("Vous avez déja voté pour cette élection");
                return message;
            }*/
       }
        System.out.println("Les users "+use);
        if (!use.isPresent()) {
            voteRepository.save(vote);
            candidat.setVoix(candidat.getVoix() + 1);
            election.setNbrvote(election.getNbrvote()+1);
            electionRepository.save(election);
            List<Candidat> candidats = candidatRepository.findAll();
            for (Candidat c : candidats) {
                c = candidatRepository.findByIdcandidat(c.getIdcandidat());
                c.setPourcentage(((float)c.getVoix()/election.getNbrvote())*100);
                candidatRepository.save(c);
            }
            candidatRepository.save(candidat);

            MessageResponse message = new MessageResponse("Bravos vous avez voté");
            return message;
        } else {
            MessageResponse message = new MessageResponse("Vous avez déja voté pour cette élection");
            return message;
        }



    }
}

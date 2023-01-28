package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.*;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.*;
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

    @Autowired
    AdministrationRepository administrationRepository;
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

    @Override
    public MessageResponse voteprojetloie( Long idAdministration, Utilisateurs idutilisateur, int vote)  {
        Vote v = new Vote();
        Administration administration = administrationRepository.findByIdAdministration(idAdministration);
        System.out.println("nombreTotal");
        System.err.println(administration.getTotalvote());
        System.out.println("nombre elue");
        System.err.println(administration.getNbredeselus());
        if (administration.getTotalvote() >= administration.getNbredeselus()){
            return new MessageResponse("Le nombre de candidat pouvant votée est atteint");
        }else {
            v.setAdministration(administration);
            v.setUtilisateurs(idutilisateur);
            v.setDate(new Date());

            Optional<Vote> use = voteRepository.findByUtilisateurs(idutilisateur);
            System.out.println("zreegfgrthgttggtg"+vote);

            System.out.println("Les users "+use);
            if (!use.isPresent()) {
                voteRepository.save(v);
                administration.setTotalvote(administration.getTotalvote() + 1);
                if (vote == 1) {
                    administration.setPour(administration.getPour() + 1);
                } else if (vote == -1) {
                    administration.setContre(administration.getContre() + 1);
                } else if (vote == 0) {
                    administration.setNeutre(administration.getNeutre() + 1);
                }
                administrationRepository.save(administration);
                MessageResponse message = new MessageResponse("Bravos vous avez voté");
                return message;
            } else {
                MessageResponse message = new MessageResponse("Vous avez déjà voté pour cette administration");
                return message;
            }
        }
    }
}

package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.*;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

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


        if (voteRepository.findById(vote.getIdvote()) != null) {

            MessageResponse message = new MessageResponse("Désoler ce vote existe déja");
            return message;
        } else {
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
    public MessageResponse creerVote(Long id_candidat,
                                     Long idelection,
                                     Utilisateurs idutilisateur,
                                     Double latitude,
                                     Double longitude) {
        Vote vote = new Vote();
        Candidat candidat = candidatRepository.findByIdcandidat(id_candidat);
        vote.setCandidat(candidat);
        Election election = electionRepository.findByIdelection(idelection);


        //debut des condition des heure & date de vote
        if (LocalDate.now().isBefore(election.getDatedebut())) {
            MessageResponse message = new MessageResponse("Désolée l'élection n'est pas encore ouverte !");
            return message;
        } else if (LocalDate.now().isEqual(election.getDatedebut()) && LocalTime.now().isAfter(election.getHeurefin())) {
            MessageResponse message = new MessageResponse("Désoler heure de vote expirer");
            return message;

            //les parties des condition des heure & date de vote
        } else if (LocalDate.now().isEqual(election.getDatedebut())) {
            if (LocalTime.now().isAfter(election.getHeuredebut()) && LocalTime.now().isBefore(election.getHeurefin())) {

                vote.setElection(new Election(idelection));
                vote.setUtilisateurs(idutilisateur);
                vote.setDate(new Date());
                vote.setLatitude(latitude);
                vote.setLongitude(longitude);

                List<Vote> listedevote = voteRepository.findByElection(vote.getElection());
                List<Vote> use1 = voteRepository.findByUtilisateurs(idutilisateur);
                for (Vote utilisateurvote : listedevote) {

                    System.out.println("zreegfgrthgttggtg" + vote.getCandidat());
                }

                if (use1.size() == 2) {
                    MessageResponse message = new MessageResponse("Désoler vous avez déja voté");
                    return message;
                } else {
                    if (use1.size() == 0) {
                        voteRepository.save(vote);
                        candidat.setVoix(candidat.getVoix() + 1);
                        election.setNbrvote(election.getNbrvote() + 1);
                        electionRepository.save(election);
                        List<Candidat> candidats = candidatRepository.findAll();
                        for (Candidat c : candidats) {
                            c = candidatRepository.findByIdcandidat(c.getIdcandidat());
                            System.out.println("pourcentage================================" + Math.round(((float) c.getVoix() / election.getNbrvote()) * 100));
                            int a = Math.round(((float) c.getVoix() / election.getNbrvote()) * 100);
                            c.setPourcentage(a);
                            candidatRepository.save(c);
                        }


/*
// faite par moi meme) Debut de la fonctionnalité de passage au second tour pour deux candidats ayant le même pourcentage de voix après l'heure de clôture

                        // triez les candidats par ordre décroissant de pourcentage de voix :
                        candidats.sort(Comparator.comparing(Candidat::getPourcentage).reversed());

                        //Ensuite, récupérez le pourcentage de voix du candidat en tête de liste (celui ayant le plus de voix) :
                        int pourcentageTeteListe = (int) candidats.get(0).getPourcentage();

                        //Parcourez la liste de candidats, et si vous rencontrez un
                        // candidat dont le pourcentage de voix est inférieur à celui du candidat en tête de liste
                        for (Candidat c : candidats) {
                            if (c.getPourcentage() < pourcentageTeteListe) {
                                c.setFinaliste(false);
                                candidatRepository.save(c);
                            }
                        }

                        //Si le candidat qui vient d'être voté est actuellement en tête de liste et qu'il n'est pas encore finaliste
                        if (candidat.getIdcandidat().equals(candidats.get(0).getIdcandidat()) && !candidats.get(0).isFinaliste()) {
                            candidats.get(0).setFinaliste(true);
                            candidatRepository.save(candidats.get(0));
                            MessageResponse message = new MessageResponse("Question de finaliste");
                        }

// Fin de la fonctionnalité de passage au second tour pour deux candidats ayant le même pourcentage de voix après l'heure de clôture

*/


                        candidatRepository.save(candidat);

                        MessageResponse message = new MessageResponse("Bravos vous avez voté");
                        return message;
                    } else {
                        for (Vote use : use1) {

                            if (use.getElection() == null) {
                                voteRepository.save(vote);
                                candidat.setVoix(candidat.getVoix() + 1);
                                election.setNbrvote(election.getNbrvote() + 1);
                                electionRepository.save(election);
                                List<Candidat> candidats = candidatRepository.findAll();
                                for (Candidat c : candidats) {
                                    c = candidatRepository.findByIdcandidat(c.getIdcandidat());
                                    c.setPourcentage(((float) c.getVoix() / election.getNbrvote()) * 100);
                                    candidatRepository.save(c);
                                }
                                candidatRepository.save(candidat);

                                MessageResponse message = new MessageResponse("Bravos vous avez voté");
                                return message;
                            } else {
                                MessageResponse message = new MessageResponse("Désoler vous avez déja voté");
                                return message;
                            }
                        }
                    }

                }

                //LES IF ET LES ELSE IF DES CONDITION DE VOTE
            } else if (LocalTime.now().isBefore(election.getHeuredebut()) && LocalTime.now().isBefore(election.getHeurefin())) {
                MessageResponse message = new MessageResponse("Désolée veillez attendre l'heure de l'élection !");
                return message;
            } else {
                MessageResponse message = new MessageResponse("Désolée l'élection est cloturée !");
                return message;
            }
        } else if (LocalDate.now().isEqual(election.getDatefin()) && LocalTime.now().isBefore(election.getHeurefin())) {
            MessageResponse message = new MessageResponse("Désolée l'élection est cloturée 2 !");
            return message;
        } else {
            MessageResponse message = new MessageResponse("Désolée la Date de l'éléction est expirée");
            return message;
        }
        //fin des condition des heure & date de vote

        return null;
    }


    //////////////////////////////////////////////////////////////////////// PROJET ///////////////////////////////////////////////////////////////


  /*  @Override
    public MessageResponse voteprojetloie(Long idAdministration,
                                          Utilisateurs idutilisateur,
                                          int vote,
                                          Double latitude,
                                          Double longitude) {
        Vote v = new Vote();
        Administration administration = administrationRepository.findByIdAdministration(idAdministration);
        System.out.println("nombreTotal");
        System.err.println(administration.getTotalvote());
        System.out.println("nombre elue");
        System.err.println(administration.getNbredeselus());


        //debut des condition des heure & date de vote
        if (LocalDate.now().isBefore(administration.getDatedebut())) {
            MessageResponse message = new MessageResponse("Désolée le vote n'est pas encore ouvert !");
            return message;
        } else if (LocalDate.now().isEqual(administration.getDatedebut()) && LocalTime.now().isAfter(administration.getHeurefin())) {
            MessageResponse message = new MessageResponse("Désoler l'heure de vote est expirer");
            return message;

            //les parties des condition des heure & date de vote
        } else if (LocalDate.now().isEqual(administration.getDatedebut())) {
            if (LocalTime.now().isAfter(administration.getHeuredebut()) && LocalTime.now().isBefore(administration.getHeurefin())) {


                if (administration.getTotalvote() >= administration.getNbredeselus()) {
                    return new MessageResponse("Le nombre de candidat pouvant votée est atteint");

                } else {
                    v.setAdministration(administration);
                    v.setUtilisateurs(idutilisateur);
                    v.setDate(new Date());
                    v.setLatitude(latitude);
                    v.setLongitude(longitude);


                    List<Vote> use1 = voteRepository.findByUtilisateurs(idutilisateur);
                    System.out.println("zreegfgrthgttggtg" + vote);

                    System.out.println("Les users " + use1);

                    if (use1.size() == 2) {
                        MessageResponse message = new MessageResponse("Désoler vous avez déjà voté");
                        return message;
                    } else {
                        if (use1.size() == 0) {
                            voteRepository.save(v);
                            administration.setTotalvote(administration.getTotalvote() + 1);
                            // pour donner sa voix de vote POUR affecter la valeur===> (1)
                            if (vote == 1) {
                                administration.setPour(administration.getPour() + 1);
                                // pour donner sa voix de vote CONTRE affecter la valeur===> (-1)
                            } else if (vote == -1) {
                                administration.setContre(administration.getContre() + 1);
                                // pour donner sa voix de vote NEUTRE affecter la valeur===> (0)
                            } else if (vote == 0) {
                                administration.setNeutre(administration.getNeutre() + 1);
                            }
                            administrationRepository.save(administration);
                            MessageResponse message = new MessageResponse("Bravos vous avez voté");
                            return message;
                        } else {
                            for (Vote use : use1) {
                                //if (use.getAdministration().equals(null) || use.equals(null)) {
                                if (use == null || use.getAdministration() == null) {
                                    voteRepository.save(v);
                                    administration.setTotalvote(administration.getTotalvote() + 1);
                                    // pour donner sa voix de vote POUR affecter la valeur===> (1)
                                    if (vote == 1) {
                                        administration.setPour(administration.getPour() + 1);
                                        // pour donner sa voix de vote CONTRE affecter la valeur===> (-1)
                                    } else if (vote == -1) {
                                        administration.setContre(administration.getContre() + 1);
                                        // pour donner sa voix de vote NEUTRE affecter la valeur===> (0)
                                    } else if (vote == 0) {
                                        administration.setNeutre(administration.getNeutre() + 1);
                                    }
                                    administrationRepository.save(administration);
                                    MessageResponse message = new MessageResponse("Bravos vous avez voté");
                                    return message;
                                } else {
                                    MessageResponse message = new MessageResponse("Désoler vous avez déjà voté");
                                    return message;
                                }
                            }
                        }
                    }
                }
            } else if (LocalTime.now().isBefore(administration.getHeuredebut()) && LocalTime.now().isBefore(administration.getHeurefin())) {
                MessageResponse message = new MessageResponse("Désolée veillez attendre l'heure de vote !");
                return message;
            } else {
                if (LocalTime.now().isBefore(administration.getHeuredebut()) && LocalTime.now().isBefore(administration.getHeurefin())) {
                    MessageResponse message = new MessageResponse("Désolée veillez attendre l'heure de vote !");
                    return message;
                } else {
                    MessageResponse message = new MessageResponse("Désolée le vote est cloturée !");
                    return message;
                }


            }
        }


        return null;
    }*/


   /* @Override
    public boolean aDejaVote(Long administrationId, Utilisateurs utilisateurs) {
        // Chercher un vote associé à l'administration et à l'utilisateur donnés
        List<Vote> vote = voteRepository.findByAdministrationIdAdministrationAndUtilisateurs(administrationId,utilisateurs);

        // Vérifier si un vote a été trouvé
        if (vote == null) {
            // L'utilisateur n'a pas encore voté pour ce projet de loi
            return false;
        } else {
            // L'utilisateur a déjà voté pour ce projet de loi
            return true;
        }
    }*/
   @Override
   public boolean aDejaVote(Long administrationId, Utilisateurs utilisateurs) {
       Optional<Vote> vote = voteRepository.findByAdministrationIdAdministrationAndUtilisateurs(administrationId, utilisateurs);
       return vote.isPresent();
   }







/////////////////////



    @Override
    public MessageResponse voteprojetloie( Long idAdministration,
                                           Utilisateurs idutilisateur,
                                           int vote,
                                           Double latitude,
                                           Double longitude)  {
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
            v.setLatitude(latitude);
            v.setLongitude(longitude);


            List<Vote> use1 = voteRepository.findByUtilisateurs(idutilisateur);
            System.out.println("zreegfgrthgttggtg"+vote);

            System.out.println("Les users "+use1);

            if (use1.size() == 2){
                MessageResponse message = new MessageResponse("Désoler vous avez déjà voté");
                return message;
            }else {
                if (use1.size() == 0){
                    voteRepository.save(v);
                    administration.setTotalvote(administration.getTotalvote() + 1);
                    // pour donner sa voix de vote POUR affecter la valeur===> (1)
                    if (vote == 1) {
                        administration.setPour(administration.getPour() + 1);
                        // pour donner sa voix de vote CONTRE affecter la valeur===> (-1)
                    } else if (vote == -1) {
                        administration.setContre(administration.getContre() + 1);
                        // pour donner sa voix de vote NEUTRE affecter la valeur===> (0)
                    } else if (vote == 0) {
                        administration.setNeutre(administration.getNeutre() + 1);
                    }
                    administrationRepository.save(administration);
                    MessageResponse message = new MessageResponse("Bravos vous avez voté");
                    return message;
                }else {
                    for (Vote use: use1){
                        if (use != null || use.getAdministration() != null) {
                            voteRepository.save(v);
                            administration.setTotalvote(administration.getTotalvote() + 1);
                            // pour donner sa voix de vote POUR affecter la valeur===> (1)
                            if (vote == 1) {
                                administration.setPour(administration.getPour() + 1);
                                // pour donner sa voix de vote CONTRE affecter la valeur===> (-1)
                            } else if (vote == -1) {
                                administration.setContre(administration.getContre() + 1);
                                // pour donner sa voix de vote NEUTRE affecter la valeur===> (0)
                            } else if (vote == 0) {
                                administration.setNeutre(administration.getNeutre() + 1);
                            }
                            administrationRepository.save(administration);
                            MessageResponse message = new MessageResponse("Bravos vous avez voté");
                            return message;
                        } else {
                            MessageResponse message = new MessageResponse("Désoler vous avez déjà voté");
                            return message;
                        }
                    }
                }

            }
            return null;
        }
    }
}
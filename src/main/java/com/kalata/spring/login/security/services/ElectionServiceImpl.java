package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Election;

import com.kalata.spring.login.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }
    @Override
    public List<Election> findAll() {
        return electionRepository.findAll();
    }


    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Override
    public Election findById(Long id) {
        return electionRepository.findById(id).orElse(null);
    }
    @Override
    public Election getById(Long id) {
        return null;
    }
    public List<Election> displayElectionsByTypeVote(Long id_type_vote) {
        List<Election> elections = new ArrayList<>();
        for (Election election : elections) {
            if (election.getType_vote().getIdtypevote() == id_type_vote) {
                elections.add(election);
            }
        }
        return elections;
    }


    @Override
    public Election save(Election election) {
        election.setNbrvote(0);
        election.setStatus(true);
        return electionRepository.save(election);
    }

        @Override
        public String delete(@PathVariable Long id) {
            electionRepository.deleteById(id);
            return"Election supprimé";

        }
    public List<Election> getElectionsByTypeVoteId(Long typeVoteId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Election> query = entityManager.createQuery("SELECT e FROM Election e WHERE e.type_vote.idtypevote = :typeVoteId", Election.class);
        query.setParameter("typeVoteId", typeVoteId);
        return query.getResultList();
    }

    //Status heure & date
    @Scheduled(fixedRate = 86400000)
    @Override
    public String Heurdate(){
        LocalDate datedujour = LocalDate.now();
        List<Election> electionterminer = electionRepository.findAll();
        for (Election electionencour : electionterminer){
            long ecart = ChronoUnit.DAYS.between(electionencour.getDatefin(),datedujour);
            if (ecart == 0){
                electionencour.setStatus(false);
                electionRepository.save(electionencour);
            }
        }
        return "Election close";
    }
}

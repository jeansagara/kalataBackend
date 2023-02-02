package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {

    Candidat findByIdcandidat(Long idcandidat);
    List<Candidat> findByElection(Election idelection);

    //empecher de... deux fois
    //boolean existsElectionByNomcandidat(String nomcandidat);
}

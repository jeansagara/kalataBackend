package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Election findByIdelection(Long idelection);

    //empecher de... deux fois
    boolean existsElectionByNomelection(String nomelection);
}

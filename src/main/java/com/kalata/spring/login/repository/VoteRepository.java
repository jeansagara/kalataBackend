package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUtilisateurs(Utilisateurs id);
    List <Vote> findByElection(Election election);
    Vote findByDate(String date);

}

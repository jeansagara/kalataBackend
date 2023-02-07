package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByUtilisateurs(Utilisateurs id);
    List <Vote> findByElection(Election election);
    Vote findByDate(String date);

   // Optional<Vote> findByUtilisateursAndAdmiNnistration(Utilisateurs idutilisateur, Administration administration);

    // Optional<Vote> findByUtilisateursAndAdministration(Utilisateurs idutilisateur, Administration administration);
}

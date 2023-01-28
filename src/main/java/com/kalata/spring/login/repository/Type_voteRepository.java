package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Type_vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Type_voteRepository extends JpaRepository<Type_vote, Long> {
    //empecher de... deux fois
    boolean existsType_voteByNom(String nom);
}

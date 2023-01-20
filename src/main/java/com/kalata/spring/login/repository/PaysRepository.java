package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {

    Pays findByNompays(String nompays);
}

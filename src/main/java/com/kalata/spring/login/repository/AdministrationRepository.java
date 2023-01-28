package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Administration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrationRepository extends JpaRepository<Administration, Long> {
    Administration findByIdAdministration(Long idAdministration);

    //empecher de... deux fois
    boolean existsAdministrationByTitre(String titre);
}

package com.kalata.spring.login.repository;


import com.kalata.spring.login.models.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {


  Optional<Utilisateurs> findByUsername(String username);
  Utilisateurs findByTelephone(String telephone);

  Utilisateurs findByBiometrie(String biometrie);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  Optional<Utilisateurs> findByBiometrieOrTelephoneOrUsername(String biometrie,String telephone,String username);


}

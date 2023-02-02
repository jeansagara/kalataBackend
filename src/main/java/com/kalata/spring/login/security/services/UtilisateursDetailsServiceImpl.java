package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.repository.RoleRepository;
import com.kalata.spring.login.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*

UserDetailsServiceest décrit comme une interface principale qui charge des données spécifiques à
l'utilisateur dans la documentation Spring.

 */

@Service
public class UtilisateursDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtilisateursRepository utilisateursRepository;

  @Autowired
  RoleRepository roleRepository;

  //recupere les details du collaborateur
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String biometrieOrTelephone) throws UsernameNotFoundException {
    Utilisateurs user = utilisateursRepository.findByBiometrieOrTelephoneOrUsername(biometrieOrTelephone,biometrieOrTelephone,biometrieOrTelephone)
        .orElseThrow(() -> new UsernameNotFoundException("collaborateur non trouvé: " + biometrieOrTelephone));
    return UtilisateursDetails.build(user);
  }



}

package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import com.kalata.spring.login.models.Utilisateurs;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UtitlisateurService {
    Role saveRole(Role role);
    Utilisateurs saveUtilisateur(Utilisateurs utilisateurs);
    public void addRoleToUtilisateur(String username, ERole roleName);
    List<Utilisateurs> lister();

}

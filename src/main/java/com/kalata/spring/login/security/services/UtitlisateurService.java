package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import com.kalata.spring.login.models.Utilisateurs;

public interface UtitlisateurService {
    Role saveRole(Role role);
    Utilisateurs saveUtilisateur(Utilisateurs utilisateurs);
    public void addRoleToUtilisateur(String username, ERole roleName);

}

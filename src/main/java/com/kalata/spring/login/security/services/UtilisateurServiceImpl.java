package com.kalata.spring.login.security.services;

import com.kalata.spring.login.SendEmail.EmailConstructor;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.repository.RoleRepository;
import com.kalata.spring.login.repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UtilisateurServiceImpl implements  UtitlisateurService{

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UtilisateursRepository utilisateursRepository;

    @Autowired
    private EmailConstructor emailConstructor;

    @Autowired
    private JavaMailSender mailSender;
   // @Autowired
    //PasswordEncoder passwordEncoder;

    @Override
    public Role saveRole(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public Utilisateurs saveUtilisateur(Utilisateurs utilisateurs) {
         utilisateursRepository.save(utilisateurs);
        mailSender.send(emailConstructor.constructNewUserEmail(utilisateurs));
        return  utilisateurs;
    }

    @Override
    public void addRoleToUtilisateur(String username, ERole roleName) {

        Optional<Utilisateurs> utilisateurs = utilisateursRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if(utilisateurs != null){
            utilisateurs.get().getRoles().add(role);
        }
    }
    @Override
    public List<Utilisateurs> lister() {
        return utilisateursRepository.findAll();
    }


}

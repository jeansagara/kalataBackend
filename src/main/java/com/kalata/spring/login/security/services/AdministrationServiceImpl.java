package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Administration;
import com.kalata.spring.login.repository.AdministrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    @Autowired
    AdministrationRepository administrationRepository;

    @Override
    public List<Administration> lister() {
        return administrationRepository.findAll();
    }

    @Override
    public Administration findById(Long id) {
        return administrationRepository.findById(id).orElse(null);
    }

    @Override
    public Administration ajout(Administration administration) {
        return administrationRepository.save(administration);
    }

    @Override
    public Administration modifier(Long id, Administration administration) {
        return this.administrationRepository.findById(id).map(tea->{
            tea.setTitre(administration.getTitre());
            tea.setDescrption(administration.getDescrption());
            tea.setImage(administration.getImage());
            tea.setDatedebut(administration.getDatedebut());
            tea.setDatefin(administration.getDatefin());
            return administrationRepository.save(tea);
        }).orElseThrow(()->new RuntimeException("Erreur"));
    }

    @Override
    public String delete(@PathVariable Long id) {
        administrationRepository.deleteById(id);
        return"Projet supprimé";
    }

    @Override
    public Administration save(Administration administration) {
        administration.setStatus(true);
        return administrationRepository.save(administration);
    }

}

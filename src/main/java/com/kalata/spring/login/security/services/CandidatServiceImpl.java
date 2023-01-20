package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.CandidatRepository;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CandidatServiceImpl implements CandidatService {

    @Autowired
  CandidatRepository candidatRepository;

    @Override
    public List<Candidat> lister() {
        return candidatRepository.findAll();
    }

    @Override
    public Candidat findById(Long id) {
        return null;
    }

    @Override
    public Candidat ajout(Candidat candidat) {
        candidat.setVoix(0);
        return candidatRepository.save(candidat);
    }

    @Override
    public Candidat modifier(Long id, Candidat candidat) {
        return this.candidatRepository.findById(id).map(tea->{
           tea.setNomcandidat(candidat.getNomcandidat());
           tea.setImagecandidat(candidat.getImagecandidat());
           tea.setImageparti(candidat.getImageparti());
           tea.setNomparti(candidat.getNomparti());
           System.out.println(tea);
            return candidatRepository.save(tea);

        }).orElseThrow(()->new  RuntimeException("erreur"));
    }

    @Override
    public String delete(@PathVariable Long id) {
       candidatRepository.deleteById(id);
        return"Candidat supprimé";

    }
}
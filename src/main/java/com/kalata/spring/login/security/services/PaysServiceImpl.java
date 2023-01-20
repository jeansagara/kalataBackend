package com.kalata.spring.login.security.services;


import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaysServiceImpl implements PaysService{



    @Autowired
    PaysRepository paysRepository;

    @Override
    public MessageResponse creer(Pays pays) {
        if(paysRepository.findByNompays(pays.getNompays()) != null){

            MessageResponse message = new MessageResponse("Cet pays existe déja");
            return message;
        }
        else{
            paysRepository.save(pays);
        MessageResponse message = new MessageResponse("Pays enregistrer avec succès");
        return message;
    }


    }

    @Override
    public List<Pays> lire() {
        return paysRepository.findAll();
    }

    @Override
    public Pays modifier(Long id, Pays pays) {

        return paysRepository.findById(id)
                .map(p-> {
                    p.setIdpays(p.getIdpays());
                    p.setNompays(p.getNompays());
                    return paysRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("Pays non trouver !"));
    }


    @Override
    public String supprimer(Long id) {
        paysRepository.deleteById(id);
        return "Pays supprimer";
    }
}

package com.kalata.spring.login.security.services;

import com.kalata.spring.login.models.Region;


import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService{

    @Autowired
    RegionRepository regionRepository;

    @Override
    public MessageResponse creer(Region region) {

        if(regionRepository.findByNomregion(region.getNomregion()) != null){

            MessageResponse message = new MessageResponse("Cette region existe déja");
            return message;
        }
        else{
            regionRepository.save(region);
            MessageResponse message = new MessageResponse("Région enregistrer avec succès");
            return message;
        }

    }

    @Override
    public List<Region> lire() {
        return regionRepository.findAll();
    }

    @Override
    public Region modifier(Long id, Region region) {

        return regionRepository.findById(id)
                .map(p-> {
                    p.setIdregion(p.getIdregion());
                    p.setNomregion(p.getNomregion());
                    return regionRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("Region non trouver !"));
    }


    @Override
    public String supprimer(Long id) {
        regionRepository.deleteById(id);
        return "Region supprimer";
    }
}

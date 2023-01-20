package com.kalata.spring.login.controllers;



import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.models.Region;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.security.services.RegionService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ToString
@RequestMapping("/api/region")
public class RegionController {


    @Autowired
    private RegionService regionService;

    @PostMapping("/creer/{id_pays}")
    public MessageResponse create(@PathVariable("id_pays") Pays id_pays, @RequestBody Region region){
        region.setPays(id_pays);
        return regionService.creer(region);
    }

    @GetMapping("/afficher")
    public List<Region> read(){
        return regionService.lire();
    }

    @PutMapping("/modifier/{id}")
    public Region update(@PathVariable Long id, @RequestBody Region region){
        return regionService.modifier(id, region);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return regionService.supprimer(id);
    }
}

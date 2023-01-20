package com.kalata.spring.login.controllers;


import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.security.services.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pays")
public class PaysController {

    @Autowired
    private PaysService paysService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creer")
    public MessageResponse create(@RequestBody Pays pays){
        return paysService.creer(pays);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/afficher")
    public List<Pays> read(){
        return paysService.lire();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modifier/{id}")
    public Pays update(@PathVariable Long id, @RequestBody Pays pays){
        return paysService.modifier(id, pays);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return paysService.supprimer(id);
    }
}

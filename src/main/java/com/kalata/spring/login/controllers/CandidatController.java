package com.kalata.spring.login.controllers;

import com.kalata.spring.login.img.SaveImage;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.security.services.CandidatService;
import lombok.ToString;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@RestController
@ToString
@RequestMapping("/api/candidats")
public class CandidatController {

    private final CandidatService candidatService;
    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter")
    public  Candidat create(
            @RequestParam("nomcandidat") String nomcandidat,
            @RequestParam("nomparti") String nomparti,
            @RequestParam("id_election") Long id_election,
            @RequestParam("icandidat") MultipartFile icandidat,
            @RequestParam("iparti") MultipartFile iparti) throws IOException {

        Candidat candidat = new Candidat( nomcandidat,nomparti);
        String icandidatname = icandidat.getOriginalFilename();
        String ipartiname = iparti.getOriginalFilename();
        candidat.setElection(new Election(id_election));
        candidat.setImagecandidat(SaveImage.save(icandidat,icandidatname));
        candidat.setImageparti(SaveImage.save(iparti,ipartiname));
        return candidatService.ajout(candidat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/afficher")
    public  List<Candidat> lister(){
        return candidatService.lister();
    }

    @PutMapping("/modifier/{idElection}")
    public Candidat modifier(@PathVariable("idElection") Long idElection,
                             @Param("nomcandidat") String nomcandidat ,
                             @Param("nomparti") String nomparti,
                             @Param("imagecandidat") MultipartFile imagecandidat,
                             @Param("imageparti") MultipartFile imageparti) throws IOException{
        Candidat candidat = new Candidat( nomcandidat,nomparti);
        String icandidatname = imagecandidat.getOriginalFilename();
        String ipartiname = imageparti.getOriginalFilename();

        candidat.setImagecandidat(SaveImage.save(imagecandidat,icandidatname));
        candidat.setImageparti(SaveImage.save(imageparti,ipartiname));
        return candidatService.modifier(idElection,candidat);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        candidatService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

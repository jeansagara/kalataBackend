package com.kalata.spring.login.controllers;

import com.kalata.spring.login.img.SaveImage;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.repository.CandidatRepository;
import com.kalata.spring.login.security.services.CandidatService;
import lombok.ToString;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600,allowCredentials = "true")

@ToString
@RequestMapping("/api/candidats")

public class CandidatController {
    private CandidatRepository candidatRepository;


    private final CandidatService candidatService;
    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;

    }


    @PostMapping("/ajouter")
    public String create(
            @RequestParam("nomcandidat") String nomcandidat,
            @RequestParam("nomparti") String nomparti,
            @RequestParam("id_election") Long id_election,
            @RequestParam("icandidat") MultipartFile icandidat,
            @RequestParam("iparti") MultipartFile iparti) throws IOException {

/*        // Methode mallé
        if (candidatRepository.existsCandidatByNomcandidat(nomcandidat)){
            return "Ce meme nom existe deja";
        }*/



        Candidat candidat = new Candidat( nomcandidat,nomparti);
        String icandidatname = icandidat.getOriginalFilename();
        String ipartiname = iparti.getOriginalFilename();
        candidat.setElection(new Election(id_election));
        candidat.setImagecandidat(SaveImage.save(icandidat,icandidatname));
        candidat.setImageparti(SaveImage.save(iparti,ipartiname));
         candidatService.ajout(candidat);
        return"Candidat ajouté avec succès";
    }


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



    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        candidatService.delete(id);
        return ResponseEntity.ok().body("Candidat supprimé avec succès.");
    }

    //Afficher les candidats par classement
    @GetMapping("/classement")
    public List<Candidat> classement() {
        List<Candidat> candidates = candidatService.lister();
        Collections.sort(candidates, new Comparator<Candidat>() {
            @Override
            public int compare(Candidat c1, Candidat c2) {
                return Integer.compare(c2.getVoix(), c1.getVoix());
            }
        });
        return candidates;
    }

}

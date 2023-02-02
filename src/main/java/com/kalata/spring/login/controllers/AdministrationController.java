package com.kalata.spring.login.controllers;


import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.Administration;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.AdministrationRepository;
import com.kalata.spring.login.repository.CandidatRepository;
import com.kalata.spring.login.repository.Type_voteRepository;
import com.kalata.spring.login.security.services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600,allowCredentials = "true")
@RestController
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/projetdelois")
public class AdministrationController {

    private final AdministrationService administrationService;
    private final Type_voteRepository type_voteRepository;

    @Autowired
    AdministrationRepository administrationRepository;

    public AdministrationController(AdministrationService administrationService,
                                    Type_voteRepository type_voteRepository) {
        this.administrationService = administrationService;
        this.type_voteRepository = type_voteRepository;
    }

    @PostMapping("/ajouter/{idadministration}")
    public String save(@RequestParam("file") MultipartFile file,
                       @RequestParam("titre") String titre,
                       @RequestParam("description") String description,
                       @RequestParam("datefin") String datefin,
                       @RequestParam("datedebut") String datedebut,
                       @RequestParam("nbredeselus") int nbredeselus,
                       @PathVariable Long idadministration) throws IOException {

        // Methode mallé
        if (administrationRepository.existsAdministrationByTitre(titre)){
            return "Ce meme nom existe deja";
        }

        Administration administration = new Administration();
        administration.setTitre(titre);
        administration.setDescrption(description);
        administration.setDatedebut(datedebut);
        administration.setDatefin(datefin);
        administration.setNbredeselus(nbredeselus);
        administration.setPour(0);
        administration.setContre(0);
        administration.setNeutre(0);
        administration.setTotalvote(0);

        Type_vote type_vote = type_voteRepository.findById(idadministration)
                .orElseThrow(() -> new IllegalArgumentException("Invalid administration id"));
        administration.setType_vote(type_vote);

        // Enregistrement de l'image et mise à jour de l'objet Administration avec le chemin de l'image
        String imgPath = StringUtils.cleanPath(file.getOriginalFilename());
        administration.setImage(imgPath);

        String uploadDir = "C:/Users/jssagara/Pictures";
        ConfigImages.saveimg(uploadDir, imgPath, file);
        administrationService.ajout(administration);
        return "Projet ajouté avec succès!";
    }

    @GetMapping("/afficher")
    public List<Administration> lister(){
        return administrationService.lister();
    }

    @GetMapping("/afficher/{id}")
    public ResponseEntity<Administration> findById(@PathVariable Long id) {
        Administration administration = administrationService.findById(id);
        if (administration == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(administration);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier/{id}"})
    public ResponseEntity<Administration> update(@PathVariable Long id,
                                           @RequestBody Administration administration) {
        administration.setIdAdministration(id);
        return ResponseEntity.ok(administrationService.save(administration));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        administrationService.delete(id);
        return ResponseEntity.ok("Projet supprimée avec succès");
    }



}



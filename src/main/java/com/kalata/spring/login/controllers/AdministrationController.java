package com.kalata.spring.login.controllers;


import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.Administration;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.Type_voteRepository;
import com.kalata.spring.login.security.services.AdministrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/projetdelois")
public class AdministrationController {

    private final AdministrationService administrationService;
    private final Type_voteRepository type_voteRepository;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier/{id}"})
    public ResponseEntity<Administration> update(@PathVariable Long id,
                                           @RequestBody Administration administration) {
        administration.setIdAdministration(id);
        return ResponseEntity.ok(administrationService.save(administration));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        administrationService.delete(id);
        return ResponseEntity.noContent().build();
    }


}



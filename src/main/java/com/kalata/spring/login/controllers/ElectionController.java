package com.kalata.spring.login.controllers;

import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.CandidatRepository;
import com.kalata.spring.login.repository.Type_voteRepository;
import com.kalata.spring.login.security.services.ElectionService;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    private final Type_voteRepository type_voteRepository;

    private CandidatRepository candidatRepository;

    public ElectionController(ElectionService electionService, Type_voteRepository type_voteRepository, CandidatRepository candidatRepository) {
        this.electionService = electionService;
        this.type_voteRepository = type_voteRepository;
        this.candidatRepository = candidatRepository;
    }

    @GetMapping
    public ResponseEntity<List<Election>> findAll() {
        return ResponseEntity.ok(electionService.findAll());
    }

    @GetMapping("/afficher/{id}")
    public ResponseEntity<Election> findById(@PathVariable Long id) {
        Election election = electionService.findById(id);
        if (election == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(election);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter/{idtypevote}")
    public Object save(@Param("file") MultipartFile file,
                       @Param("images") String images,
                       @Param("nomelection") String nomelection,
                       @Param("description") String description,
                       @Param("datefin") String datefin,
                       @Param("datedebut") String datedebut,
                       @PathVariable("idtypevote") Long idtypevote) throws IOException {

        Election election = new Election();
        election.setNomelection(nomelection);
        election.setDescription(description);
        election.setDatedebut(datedebut);
        election.setDatefin(datefin);

        Type_vote type_vote = type_voteRepository.getReferenceById(idtypevote);
        election.setType_vote(type_vote);

        // Enregistrement de l'image et mise à jour de l'objet Election avec le chemin de l'image
        String img = StringUtils.cleanPath(file.getOriginalFilename());
        election.setImages(img);
        String uploaDir = "C:/Users/jssagara/Pictures";
        ConfigImages.saveimg(uploaDir, img, file);
        electionService.save(election);
        return "Election ajouter avec succès!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/modifier/{id}"})
    public ResponseEntity<Election> update(@PathVariable Long id,
                                           @RequestBody Election election) {
        election.setId(id);
        return ResponseEntity.ok(electionService.save(election));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        electionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/candidatParElection/{idelection}")
    public List<Candidat> candidatParElection(@PathVariable Election idelection) {
        List<Candidat> cnd = candidatRepository.findByElection(idelection);
        System.out.println("tyuiopcvjklm");
        return candidatRepository.findByElection(idelection);
    }
}
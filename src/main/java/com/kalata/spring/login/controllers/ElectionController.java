package com.kalata.spring.login.controllers;

import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.Candidat;
import com.kalata.spring.login.models.Election;
import com.kalata.spring.login.models.Type_vote;
import com.kalata.spring.login.repository.CandidatRepository;
import com.kalata.spring.login.repository.ElectionRepository;
import com.kalata.spring.login.repository.Type_voteRepository;
import com.kalata.spring.login.security.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(value = {"http://localhost:8100","http://localhost:4200"}, maxAge = 3600,allowCredentials = "true")
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    @Autowired
     ElectionRepository electionRepository;

    private final Type_voteRepository type_voteRepository;

    private CandidatRepository candidatRepository;

    public ElectionController(ElectionService electionService,
                              Type_voteRepository type_voteRepository,
                              CandidatRepository candidatRepository) {
        this.electionService = electionService;
        this.type_voteRepository = type_voteRepository;
        this.candidatRepository = candidatRepository;
    }

    @GetMapping("/afficherAllElection")
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



    @GetMapping("/type-vote/{typeVoteId}")
    public List<Election> getElectionsByTypeVoteId(@PathVariable Long typeVoteId) {
        return electionService.getElectionsByTypeVoteId(typeVoteId);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter/{idtypevote}")
    public Object save(@Param("file") MultipartFile file,
                       @Param("images") String images,
                       @Param("nomelection") String nomelection,
                       @Param("description") String description,
                       @Param("soustitre") String soustitre,
                       @Param("datefin") String datefin1,
                       @Param("datedebut") String datedebut1,
                       @PathVariable("idtypevote") Long idtypevote) throws IOException {

        // Methode mallé
        if (electionRepository.existsElectionByNomelection(nomelection)) {
            return "Ce meme nom existe deja";
        }
        System.out.print(datefin1);

        // METHODE status
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate datedebut = LocalDate.parse(datedebut1, formatter);
         LocalDate datefin = LocalDate.parse(datefin1, formatter);
         // METHODE status

        Election election = new Election();
        election.setNomelection(nomelection);
        election.setDescription(description);
        election.setSoustitre(soustitre);
        election.setDatedebut(datedebut);
        election.setDatefin(datefin);

        Type_vote type_vote = type_voteRepository.getReferenceById(idtypevote);
        election.setType_vote(type_vote);

        // Enregistrement de l'image et mise à jour de l'objet Election avec le chemin de l'image
        String img = StringUtils.cleanPath(file.getOriginalFilename());
        election.setImages(img);
        String uploaDir = "C:\\Users\\jssagara\\Desktop\\JeanProjetSoutenance\\kalata\\src\\assets\\images";
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
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        electionService.delete(id);
        return ResponseEntity.ok("Election supprimée avec succès");
    }

    //Afficher les candidats par election
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/candidatParElection/{idelection}")
    public List<Candidat> candidatParElection(@PathVariable Election idelection) {
        List<Candidat> cnd = candidatRepository.findByElection(idelection);
        return candidatRepository.findByElection(idelection);
    }
}

package com.kalata.spring.login.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idcandidat;
    private String nomcandidat;
    private String nomparti;
    private String imagecandidat;
    private String imageparti;
    private int voix;
    private double pourcentage;


    @JoinColumn(name = "id_election") // ICI IMPORTANT
    @ManyToOne
    private Election election;

    public Candidat(String nomcandidat,
                    String nomparti) {
        this.nomcandidat=nomcandidat;
        this.nomparti=nomparti;
    }

    public Candidat(String nomcandidat,
                    String nomparti,
                    String imagecandidat,
                    String imageparti) {
        this.nomcandidat=nomcandidat;
        this.nomparti=nomparti;
        this.imagecandidat=imagecandidat;
        this.imageparti=imageparti;

    }
}

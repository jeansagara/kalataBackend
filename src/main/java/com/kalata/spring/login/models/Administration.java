package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "projetdelois")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idAdministration;
    private String titre;
    private String image;
    private String descrption;
    private String datefin;
    private String datedebut;
    private int nbredeselus;
    private int pour;
    private int contre;
    private int neutre;
    private int totalvote;
    private Boolean status;




    @ManyToOne
    @JoinColumn(name = "id_typevote")
    private Type_vote type_vote;

    public Administration(Long idadministration) {
    }

    public void setImages(String img) {
    }
}

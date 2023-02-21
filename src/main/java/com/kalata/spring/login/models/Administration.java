package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate datefin;
    private LocalDate datedebut;
    private int nbredeselus;
    private int pour;
    private int contre;
    private int neutre;
    private int totalvote;
    private Boolean status;




    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_typevote")
    private Type_vote type_vote;

    public Administration(Long idadministration) {
    }

    public void setImages(String img) {
    }
}

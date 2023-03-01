package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idelection;
    private String nomelection;
    private String images;

    private String description;
    private String soustitre;

    private LocalDate datedebut;
    private LocalDate datefin;

    private LocalTime heuredebut;
    private LocalTime heurefin;


    /*private Boolean status;*/
    private int nbrvote;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_type_vote")
    private Type_vote type_vote;

    public Election(long election) {
        idelection = election;
    }

    public void setId(Long id) {
        this.idelection = id;
    }


}

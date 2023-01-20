package com.kalata.spring.login.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private String datefin;
    private String datedebut;
    private int nbrvote;


    @ManyToOne
    @JoinColumn(name = "id_type_vote")
    private Type_vote type_vote;

    public Election(long election) {
        idelection = election;
    }

    public void setId(Long id) {
        this.idelection = id;
    }


}

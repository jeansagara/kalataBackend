package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Type_vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtypevote;
    private String nom;



}

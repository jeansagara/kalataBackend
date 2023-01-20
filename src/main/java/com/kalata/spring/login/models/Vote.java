package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idvote;
    private Date date;

@ManyToOne
@JoinColumn(name = "userid")
private Utilisateurs utilisateurs;

   @ManyToOne
   private Election election;

  @ManyToOne
  @JoinColumn(name = "idcandidat")
  private Candidat candidat;

}

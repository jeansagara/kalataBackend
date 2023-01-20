
package com.kalata.spring.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idutilisateur;
    private String username;

    @Email
    private String email;
    private String password;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //-------------------------New---------------------

    private Date datenaissance;
    private String lieunaissance;
    private int age;
    private String sexe;
    private String photo;
    private String qrcode;
    private String region;
    private String pays;

    public Utilisateurs(String username, String email, Date datenaissance , String lieunaissance ,  String password, int age, String sexe, String photo, String qrcode, String pays, String region) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.sexe = sexe;
        this.datenaissance = datenaissance;
        this.lieunaissance = lieunaissance;
        this.photo = photo;
        this.qrcode = qrcode;
        this.pays = pays;
        this.region = region;
        this.email = email;
    }

}

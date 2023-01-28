package com.kalata.spring.login.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalata.spring.login.models.Utilisateurs;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class UtilisateursDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long idutilisateur;
  private String username;
  private Date datenaissance;
  private String sexe;
  private String biometrie;
  private String telephone;
  private String email;
 //private String adresse;

  @JsonIgnore
  private String password;

  /*
    GrantedAuthorityest une autorité accordée au mandant. Ces autorités sont généralement
    des "rôles", tels que ROLE_ADMIN, ROLE_PM, ROLE_USER…
   */
  private Collection<? extends GrantedAuthority> authorities;

  public UtilisateursDetails(Long idutilisateur,
                             String username,
                             Date datenaissance,
                             String sexe,
                             String biometrie,
                             String telephone,
                             String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
    this.idutilisateur = idutilisateur;
    this.username = username;
    this.datenaissance = datenaissance;
    this.sexe = sexe;
    this.biometrie = biometrie;
    this.telephone = telephone;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  //return l'user avec tous ces droits et toutes ces informations
  public static UtilisateursDetails build(Utilisateurs user) {


    //Stream est utilisée pour traiter des collections d'objets
    List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());

    //on cree retourne un objet UtilisateursDetails
    return new UtilisateursDetails(
            user.getIdutilisateur(),
            user.getUsername(),
            user.getDatenaissance(),
            user.getSexe(),
            user.getTelephone(),
            user.getBiometrie(),
            user.getEmail(),
            user.getPassword(),
            authorities
    );
  }
  //recupere les information l'user de l'utilisateur
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getIdutilisateur() {
    return idutilisateur;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UtilisateursDetails user = (UtilisateursDetails) o;
    return Objects.equals(idutilisateur, user.idutilisateur);
  }
}

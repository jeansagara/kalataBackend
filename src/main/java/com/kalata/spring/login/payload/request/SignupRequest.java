
package com.kalata.spring.login.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  //----------------new----------------

  private int age;

  @NotBlank
  @Size(max = 20)
  private String sexe;

  @NotBlank
  @Size(max = 90)
  private String photo;

  @NotBlank
  @Size(max = 40)
  private String qrcode;

  @NotBlank
  @Size(max = 35)
  private String region;

  @NotBlank
  @Size(max = 35)
  private String pays;

}
/*
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;



  private int age;

  private String sexe;

  private String region;

  private String pays;

  @NotBlank
  @Size(min = 3, max = 50)
  @Email
  private String email;


  @NotBlank
  @Size(min = 3, max = 80)
  private String nomcomplet;

  @NotBlank
  @Size(min = 3, max = 25)
  private String photo;

  @NotBlank
  @Size(min = 3, max = 120)
  private String adresse;

  @NotBlank
  @Size(min = 8, max = 8)
  private String numerotelephone;


  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  ////////////

  public int getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getSexe() {
    return sexe;
  }

  public void setSexe(String sexe) {
    this.sexe = sexe;
  }


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }


  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  ////////

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
*/

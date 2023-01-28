package com.kalata.spring.login.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;

    private String sexe;


    private String email;
    private String adresse;

    private List<String> roles;

   /* public JwtResponse(String accessToken, Long id, String username, String email, String sexe, String adresse,  List<String> roles
                       ) {
        this.token = accessToken;
        this.id = id;
        this.us
        this.email=email;
        this.username = username;
        this.adresse = adresse;

        this.sexe = sexe;


        this.email = email;
        this.roles = roles;

    }*/

    /*public JwtResponse(String accessToken, Long id, String username, Integer age, String sexe, String email, String region, String pays, List<String> roles, String adresse, String nomcomplet, String photo) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.sexe = sexe;
        this.region = region;
        this.pays = pays;

    }

    public JwtResponse(String accessToken, Long id,
                       String username,
                       int age,
                       String sexe,
                       String region,
                       String pays,
                       String email,
                       List<String> roles,
                       String adresse,
                       String photo) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;

        this.sexe = sexe;

        this.email = email;
        this.roles = roles;
        this.adresse = adresse;

    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/

    public JwtResponse(String token, Long id, String username, String sexe, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.sexe = sexe;
        this.email = email;
        this.adresse = adresse;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

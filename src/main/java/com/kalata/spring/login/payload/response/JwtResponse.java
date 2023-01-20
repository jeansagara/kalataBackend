package com.kalata.spring.login.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private Integer age;
    private String sexe;
    private String region;
    private String pays;
    private String email;
    private String adresse;
    private String photo;
    private String nomcomplet;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, int age, String sexe, String region, String pays, List<String> roles, String adresse,
                       String photo, String nomcomplet) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.sexe = sexe;
        this.region = region;
        this.pays = pays;
        this.email = email;
        this.roles = roles;
        this.adresse = adresse;
        this.photo = photo;
        this.nomcomplet = nomcomplet;
    }

    public JwtResponse(String accessToken, Long id, String username, Integer age, String sexe, String email, String region, String pays, List<String> roles, String adresse, String nomcomplet, String photo) {
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
        this.age = age;
        this.sexe = sexe;
        this.region = region;
        this.pays = pays;
        this.email = email;
        this.roles = roles;
        this.adresse = adresse;
        this.photo = photo;
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
    }

    public List<String> getRoles() {
        return roles;
    }
}

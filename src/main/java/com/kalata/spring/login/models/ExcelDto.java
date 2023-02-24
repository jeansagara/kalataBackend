package com.kalata.spring.login.models;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExcelDto {
    String username;
    String biometrie;
    String datenaissance;
    String telephone;
    String sexe;
    String email;
    String password;
}
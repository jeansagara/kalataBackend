
package com.kalata.spring.login.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;


  private String biometrie;


  private String telephone;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 0, max = 400)
  private String password;

  //----------------new----------------

  private long age;

  @NotBlank
  @Size(max = 20)
  private String sexe;

  @NotBlank
  @Size(max = 20)
  private Date datenaissance;

}


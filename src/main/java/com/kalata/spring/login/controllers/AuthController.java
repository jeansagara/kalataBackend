package com.kalata.spring.login.controllers;


import com.kalata.spring.login.EXCEL.FilleExcel;
import com.kalata.spring.login.SendEmail.EmailConstructor;
import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.payload.request.LoginRequest;
import com.kalata.spring.login.payload.request.SignupRequest;
import com.kalata.spring.login.payload.response.JwtResponse;
import com.kalata.spring.login.payload.response.MessageResponse;
import com.kalata.spring.login.repository.RoleRepository;
import com.kalata.spring.login.repository.UtilisateursRepository;
import com.kalata.spring.login.security.services.jwt.JwtUtils;
import com.kalata.spring.login.security.services.UtilisateursDetails;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
@ToString
public class AuthController {

  @Autowired
  private EmailConstructor emailConstructor;

  @Autowired
  private JavaMailSender mailSender;

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UtilisateursRepository utilisateursRepository;


  @Autowired
  RoleRepository roleRepository;

  //encoder du password
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  //@Valid assure la validation de l'ensemble de l'objet
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


    String url = "C:/Users/jssagara/Pictures/springimg";


    Authentication authentication = authenticationManager.authenticate(
            //on lui fournit un objet avec username et password fournit par l'admin
            new UsernamePasswordAuthenticationToken(loginRequest.getBiometrieOrTelephone(), loginRequest.getPassword()));


    //on stocke les informations de connexion de l'utilisateur actuelle souhaiter se connecter dans SecurityContextHolder
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //on envoie encore les infos au generateur du token
    String jwt = jwtUtils.generateJwtToken(authentication);

    //on recupere les infos de l'user
    UtilisateursDetails utilisateursDetails = (UtilisateursDetails) authentication.getPrincipal();

    //on recupere les roles de l'users
    List<String> roles = utilisateursDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    log.info("conexion controlleur");

    //on retourne une reponse, contenant l'id username, e-mail et le role du collaborateur
    return ResponseEntity.ok(new JwtResponse(jwt,
                    utilisateursDetails.getIdutilisateur(),
                    utilisateursDetails.getUsername(),
                    utilisateursDetails.getSexe(),
                    utilisateursDetails.getEmail(), roles
                    //utilisateursDetails.getAdresse(),
            )

    );
  }

  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerDefaultUser(

                                               @Param("username") String username,
                                               @Param("biometrie") String biometrie,
                                               @Param("telephone") String telephone,
                                               @Param("sexe") String sexe,
                                               @Param("datenaissance") Date datenaissance,
                                               @Param("email") String email,
                                               @Param("password") String password,
                                               @Param("role") String role

  ) throws IOException {

    //chemin de stockage des images
    String url = "C:/Users/jssagara/Pictures/springimg";


/*    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);*/

    //converssion du string reçu en classe SignupRequest
    //SignupRequest signUpRequest = new JsonMapper().readValue(donneesuser, SignupRequest.class);

    Set<String> rolesvenu = new HashSet<>();
    rolesvenu.add(role);

    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setUsername(username);
    signUpRequest.setSexe(sexe);
    signUpRequest.setBiometrie(biometrie);
    signUpRequest.setTelephone(telephone);
    signUpRequest.setEmail(email);
    signUpRequest.setDatenaissance(new Date());
    signUpRequest.setPassword(password);
    signUpRequest.setRole(rolesvenu);



    if (utilisateursRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
    }

    if (utilisateursRepository.existsByEmail(signUpRequest.getEmail())) {

      //confectionne l'objet de retour à partir de ResponseEntity(une classe de spring boot) et MessageResponse
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
    }
    //System.err.println(signUpRequest);
    // Create new user's account
    Utilisateurs utilisateurs = new Utilisateurs();

    utilisateurs.setUsername(signUpRequest.getUsername());
    utilisateurs.setEmail(signUpRequest.getEmail());
    utilisateurs.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateurs.setSexe(signUpRequest.getSexe());
    utilisateurs.setBiometrie(signUpRequest.getBiometrie());
    utilisateurs.setTelephone(signUpRequest.getTelephone());
    utilisateurs.setDatenaissance(signUpRequest.getDatenaissance());


    System.err.println(utilisateurs);

    //on recupere le role de l'user dans un tableau ordonné de type string
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    //roles.add(strRoles);

    if (strRoles == null) {
      System.out.println("####################################" + signUpRequest.getRole() + "###########################################");

      //on recupere le role de l'utilisateur
      Role userRole = roleRepository.findByName(ERole.ROLE_ELECTEUR);
      roles.add(userRole);//on ajoute le role de l'user à roles
    } else {
      strRoles.forEach(rol -> {//on parcours le role
        //dans le cas écheant
        if ("admin".equals(rol)) {//si le role est à égale à admin
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
          roles.add(adminRole);
        } else if ("superadmin".equals(rol)) {//si le role est à égale à admin
          Role electeurRole = roleRepository.findByName(ERole.ROLE_SUPERADMIN);
          roles.add(electeurRole);
        } else {//on recupere le role de l'utilisateur
          Role userRole = roleRepository.findByName(ERole.ROLE_ELECTEUR);
          roles.add(userRole);

        }
      });
    }

    //on ajoute le role au Electeur
    utilisateurs.setRoles(roles);
    System.out.println("############VOICI MON UTILISATEUR###############");
    System.out.println(utilisateurs);

    utilisateursRepository.save(utilisateurs);
    mailSender.send(emailConstructor.constructNewUserEmail(utilisateurs));
    return ResponseEntity.ok(new MessageResponse("Electeur enregistré avec succès!"));

  }


  @PostMapping("/electeur")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Utilisateurs> importer(@RequestParam("file") MultipartFile file) throws IOException {
    List<Utilisateurs> electeurs = FilleExcel.saveElecteur(file);

    for (Utilisateurs u: electeurs){
      registerDefaultUser(u.getUsername(), u.getBiometrie(), u.getTelephone(), u.getSexe(), u.getDatenaissance(), u.getEmail(), u.getPassword(),"electeur");
    }
    return electeurs;
  }

}


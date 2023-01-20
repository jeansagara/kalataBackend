package com.kalata.spring.login.controllers;


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
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
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



   // String url = "C:/Users/mkkeita/Desktop/projects/angular/interfaceMaliTourist/src/assets/images";
    String url = "C:/Users/jssagara/Pictures/springimg";


    Authentication authentication = authenticationManager.authenticate(
            //on lui fournit un objet avec username et password fournit par l'admin
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


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
                         utilisateursDetails.getAge(),
                         utilisateursDetails.getSexe(),
                         utilisateursDetails.getRegion(),
                         utilisateursDetails.getPays(),
                         utilisateursDetails.getEmail(), roles,
                         utilisateursDetails.getAdresse(),
                         utilisateursDetails.getPhoto()
                         )

    );
  }

  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerDefaultUser(@Valid @RequestParam(value = "file", required = true) MultipartFile file,
                                        @Param("username") String username,
                                        @Param("age") int age,
                                        @Param("sexe") String sexe,
                                        @Param("region") String region,
                                        @Param("pays") String pays,
                                        @Param("email") String email,
                                        @Param("password") String password,
                                               @Param("role") String role,
                                        @Param("qrcode") String qrcode
                                        ) throws IOException {

    //chemin de stockage des images
    String url = "C:/Users/jssagara/Pictures/springimg";


    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);

    //converssion du string reçu en classe SignupRequest
    //SignupRequest signUpRequest = new JsonMapper().readValue(donneesuser, SignupRequest.class);

    Set<String> rolesvenu = new HashSet<>();
    rolesvenu.add(role);

    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setUsername(username);
    signUpRequest.setAge(age);
    signUpRequest.setSexe(sexe);
    signUpRequest.setRegion(region);
    signUpRequest.setPays(pays);
    signUpRequest.setEmail(email);
    signUpRequest.setPassword(password);
    signUpRequest.setQrcode(qrcode);
    signUpRequest.setRole(rolesvenu);


    signUpRequest.setPhoto(nomfile);

    System.out.println(signUpRequest);

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

    // Create new user's account
    Utilisateurs utilisateurs = new Utilisateurs();
    utilisateurs.setUsername(signUpRequest.getUsername());
    utilisateurs.setEmail(signUpRequest.getEmail());
    utilisateurs.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateurs.setAge(signUpRequest.getAge());
    utilisateurs.setSexe(signUpRequest.getSexe());
    utilisateurs.setPhoto(signUpRequest.getPhoto());
    utilisateurs.setQrcode(signUpRequest.getQrcode());
    utilisateurs.setPays(signUpRequest.getPays());
    utilisateurs.setRegion(signUpRequest.getRegion());


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
    if (age < 18){
      return ResponseEntity.ok(new MessageResponse("Vous n'êtes pas autorisé à voter car vous avez moins de 18 ans"));

    }else{
      utilisateursRepository.save(utilisateurs);
      mailSender.send(emailConstructor.constructNewUserEmail(utilisateurs));
      return ResponseEntity.ok(new MessageResponse("Electeur enregistré avec succès!"));

    }




  }


  public ResponseEntity<?> registerUser(@Valid @RequestParam(value = "file", required = true) MultipartFile file,
                                        @Param("username") String username,
                                        @Param("age") int age,
                                        @Param("sexe") String sexe,
                                        @Param("region") String region,
                                        @Param("pays") String pays,
                                        @Param("email") String email,
                                        @Param("password") String password,
                                        @Param("qrcode") String qrcode
  ) throws IOException {

    //chemin de stockage des images
    String url = "C:/Users/jssagara/Pictures/springimg";


    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);

    //converssion du string reçu en classe SignupRequest
    //SignupRequest signUpRequest = new JsonMapper().readValue(donneesuser, SignupRequest.class);

    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setUsername(username);
    signUpRequest.setAge(age);
    signUpRequest.setSexe(sexe);
    signUpRequest.setRegion(region);
    signUpRequest.setPays(pays);
    signUpRequest.setEmail(email);
    signUpRequest.setPassword(password);
    signUpRequest.setQrcode(qrcode);


    signUpRequest.setPhoto(nomfile);

    System.out.println(signUpRequest);

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

    // Create new user's account
    Utilisateurs utilisateurs = new Utilisateurs();
    utilisateurs.setUsername(signUpRequest.getUsername());
    utilisateurs.setEmail(signUpRequest.getEmail());
    utilisateurs.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateurs.setAge(signUpRequest.getAge());
    utilisateurs.setSexe(signUpRequest.getSexe());
    utilisateurs.setPhoto(signUpRequest.getPhoto());
    utilisateurs.setQrcode(signUpRequest.getQrcode());
    utilisateurs.setPays(signUpRequest.getPays());
    utilisateurs.setRegion(signUpRequest.getRegion());


    //on recupere le role de l'user dans un tableau ordonné de type string
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      System.out.println("####################################" + signUpRequest.getRole() + "###########################################");

      //on recupere le role de l'utilisateur
      Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN);
      roles.add(userRole);//on ajoute le role de l'user à roles
    } else {
      strRoles.forEach(role -> {//on parcours le role
        switch (role) {
          case "admin"://si le role est à égale à admin
            Role adminRole = roleRepository.findByName(ERole.ROLE_SUPERADMIN);
            roles.add(adminRole);

            break;
          case "electeur"://si le role est à égale à admin
            Role electeurRole = roleRepository.findByName(ERole.ROLE_ELECTEUR);
            roles.add(electeurRole);

            break;
          default://dans le cas écheant

            //on recupere le role de l'utilisateur
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            roles.add(userRole);
        }
      });
    }
    //on ajoute le role au Electeur
    utilisateurs.setRoles(roles);
    System.out.println("############VOICI MON UTILISATEUR###############");
    System.out.println(utilisateurs);
    utilisateursRepository.save(utilisateurs);
    strRoles.forEach(role -> {//on parcours le role
      switch (role) {
        case "admin"://si le role est à égale à admin
          mailSender.send(emailConstructor.constructNewUserEmail(utilisateurs));
          break;


        default://dans le cas écheant

      }
    });






    return ResponseEntity.ok(new MessageResponse("Electeur enregistré avec succès!"));
  }
}

package com.kalata.spring.login.controllers;


import com.kalata.spring.login.EXCEL.FilleExcel;
import com.kalata.spring.login.SendEmail.EmailConstructor;
import com.kalata.spring.login.img.ConfigImages;
import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.ExcelDto;
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
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CrossOrigin(value = {"http://localhost:8100","http://localhost:4200"}, maxAge = 3600,allowCredentials = "true")
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
  public ResponseEntity<?> registerDefaultUser(@Param("username") String username,
                                               @Param("biometrie") String biometrie,
                                               @Param("telephone") String telephone,
                                               @Param("sexe") String sexe,
                                               @Param("datenaissance") String datenaissance,
                                               @Param("email") String email,
                                               @Param("password") String password,
                                               @Param("role") String role

  ) throws IOException, ParseException {

    Date dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(datenaissance);

    //Date date = dateFormat.parse(datenaissance);

    System.out.println(dateFormat);

    //chemin de stockage des images
    String url = "C:/Users/jssagara/Pictures/springimg";

    System.out.println("email: "+ email);
    System.out.println("Date 1: "+ datenaissance);
    System.out.println("Date 2: "+ dateFormat);

    Date datejour = new Date();
    long ageer = datejour.getTime() - dateFormat.getTime() ;


    TimeUnit time = TimeUnit.DAYS;
    long diffrence = time.convert(ageer, TimeUnit.MILLISECONDS);
      long age = diffrence/365;
    System.out.println("The difference in years is : "+age);

    System.out.println("l'ecart de date est : "+ ageer);

    if (age < 18) {
      return ResponseEntity.ok(new MessageResponse("Vous n'avez pas l'age de voter !"));//ResponseEntity.badRequest().body("Vous n'avez pas l'age de voter");
    } else {
      Set<String> rolesvenu = new HashSet<>();
      rolesvenu.add(role);
      SignupRequest signUpRequest = new SignupRequest();
      signUpRequest.setUsername(username);
      signUpRequest.setSexe(sexe);
      signUpRequest.setBiometrie(biometrie);
      signUpRequest.setTelephone(telephone);
      signUpRequest.setEmail(email);
      signUpRequest.setDatenaissance(dateFormat);
      signUpRequest.setAge(age);
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
      utilisateurs.setPassword(encoder.encode(password));
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
          } else if ("administration".equals(rol)){//on récupère le role de l'administration
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMINISTRATION);
            roles.add(userRole);
          } else {//on récupère le role de l'utilisateur
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
      // mailSender.send(emailConstructor.constructNewUserEmail(utilisateurs));
      return ResponseEntity.ok(new MessageResponse("Enregistré avec succès!"));
    }

  }

  //IMPORTATION FILE EXCEL
  @PostMapping("/electeur")
  public List<ExcelDto> importer(@Param("excel") MultipartFile excel) throws IOException, ParseException {
    List<ExcelDto> excelDtos = FilleExcel.saveElecteur(excel);
    List<Utilisateurs> utilisateurs = new ArrayList<>();
    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName(ERole.ROLE_ADMINISTRATION);
    roles.add(userRole);
    excelDtos.forEach(excelDto -> {
      Utilisateurs utilisateur = new Utilisateurs();
      utilisateur.setRoles(roles);

            String U = excelDto.getPassword();
            System.out.println("le pass "+ U);
      utilisateur.setPassword(U);


//      encoder.encode(utilisateur.getPassword());
      utilisateur.setSexe(excelDto.getSexe());
      Date dateFormat = new Date();
    //  try {
      //  dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(excelDto.getDatenaissance());
      //} catch (ParseException e) {
       // throw new RuntimeException(e);
      //}
      utilisateur.setDatenaissance(dateFormat);
      utilisateur.setBiometrie(excelDto.getBiometrie());
      utilisateur.setTelephone(excelDto.getTelephone());
      utilisateur.setUsername(excelDto.getUsername());
      utilisateur.setEmail(excelDto.getEmail());
      utilisateurs.add(utilisateur);
    });
    utilisateursRepository.saveAll(utilisateurs);
    assert excelDtos != null;
    for (ExcelDto exceldata :
            excelDtos) {

      registerDefaultUser(
              exceldata.getUsername(),
              exceldata.getBiometrie(),
              exceldata.getTelephone(),
              exceldata.getSexe(),
              exceldata.getDatenaissance(),
              exceldata.getEmail(),
//              exceldata.getPassword(),
              encoder.encode(exceldata.getPassword()),
              null
              );
    }

    return excelDtos;
  }

}



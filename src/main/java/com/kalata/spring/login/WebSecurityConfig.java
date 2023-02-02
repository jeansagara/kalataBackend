package com.kalata.spring.login;

import com.kalata.spring.login.security.services.jwt.AuthEntryPointJwt;
import com.kalata.spring.login.security.services.jwt.AuthTokenFilter;
import com.kalata.spring.login.security.services.UtilisateursDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true)
public class WebSecurityConfig {



  @Autowired
  UtilisateursDetailsServiceImpl userDetailsService;


 /*
  Si l'utilisateur demande une ressource HTTP sécurisée sans être authentifié,
  AuthenticationEntryPointsera appelé.
   A ce moment, une AuthenticationExceptionest lancée, commence()la méthode sur
   le point d'entrée est déclenchée :
  */
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }


  /*
  DaoAuthenticationProvider, qui récupère les détails de l'utilisateur à partir d'un fichier
  UserDetailsService
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {


      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    /*
    - UserDetailsService L'interface a une méthode pour charger l'utilisateur par nom d' utilisateur
    et renvoie un UserDetailsobjet que Spring Security peut utiliser pour l'authentification et la
    validation.

    – UserDetails contient les informations nécessaires (telles que : nom d'utilisateur, mot de passe,
     autorités) pour construire un objet d'authentification.
     */

      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  /*
  Pour activer la sécurité HTTP dans Spring, nous devons créer un bean SecurityFilterChain

  La configuration ci-dessus garantit que toute demande adressée à l'application est authentifiée
  avec une connexion basée sur un formulaire ou une authentification de base HTTP.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/bienvenue/**").permitAll()
            .antMatchers("/api/roles/**").permitAll()
            .antMatchers("/**").permitAll()

          //  .antMatchers("/api/typevote").permitAll()
            .antMatchers("/api/elections").permitAll()
            .antMatchers("/api/pays").permitAll()
            .antMatchers("/api/region").permitAll()
            .antMatchers("/api/vote").permitAll()
            .antMatchers("/api/test/**").permitAll()
            .antMatchers("/api/projetdelois/**").permitAll()
            .antMatchers("/api/voteprojet/**").permitAll()
            .antMatchers("/api/candidats/**").permitAll()
            .antMatchers("/api/projetdelois/**").permitAll()


            .anyRequest().authenticated();
           // .and()
            //.oauth2Login();
   // http.formLogin();

    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

}

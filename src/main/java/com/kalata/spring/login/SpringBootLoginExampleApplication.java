package com.kalata.spring.login;

import com.kalata.spring.login.Twilio.config.TwilioConfig;
import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import com.kalata.spring.login.models.Utilisateurs;
import com.kalata.spring.login.repository.RoleRepository;
import com.kalata.spring.login.repository.UtilisateursRepository;
import com.kalata.spring.login.security.services.UtitlisateurService;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.kalata.spring.login.models.ERole.*;

@SpringBootApplication
@EnableScheduling
public class SpringBootLoginExampleApplication implements CommandLineRunner {

	////Partie Twilio (début)////
	@Autowired
	private TwilioConfig twilioConfig;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	UtitlisateurService utitlisateurService;

	@Autowired
	UtilisateursRepository utilisateursRepository;

	@Autowired
	PasswordEncoder encoder;


	@PostConstruct
	public void initTwilio() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
////Partie Twilio (fin)////


	public static void main(String[] args) {
		SpringApplication.run(SpringBootLoginExampleApplication.class, args);
	}

	@Primary
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.findAll().size() == 0) {
			Role r1 = utitlisateurService.saveRole(new Role(null, ROLE_SUPERADMIN));
			Role r2 = utitlisateurService.saveRole(new Role(null, ROLE_ADMIN));
			Role r3 = utitlisateurService.saveRole(new Role(null, ROLE_ELECTEUR));
			Role r4 = utitlisateurService.saveRole(new Role(null, ROLE_ADMINISTRATION));


		if (utilisateursRepository.findAll().size() ==0){
			Set<Role> roles = new HashSet<>();
			Role role = roleRepository.findByName(ROLE_ADMIN);
			roles.add(role);
			Date datenaissance =  new Date(2022, 11, 11);
			Date dt = new Date(23,10,20);

			Utilisateurs u1 = new Utilisateurs(null,"jean", "jeansagara272@gmail.com",encoder.encode("sagara"),roles,dt, "homme", "1234567890","99300099",25);
			u1.setRoles(roles);
			utilisateursRepository.save(u1);

			System.out.println(u1);

		}

	}

	}

}

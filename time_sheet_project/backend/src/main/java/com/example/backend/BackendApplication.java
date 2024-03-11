package com.example.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.core.service_Implementation.AuthenticationServiceImpl;
import com.example.backend.data.repository.IUserJPARepository;

import static com.example.backend.enums.Role.ADMIN;
import static com.example.backend.enums.UserStatus.ACTIVE;

import java.util.HashSet;
import java.util.Set;;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, IUserJPARepository userRepository,
			PasswordEncoder passwordEncode) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			Role userRole = roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new ApplicationUser(1, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);

			Set<Role> rolesForUser = new HashSet<>();
			rolesForUser.add(userRole);

			User user = new ApplicationUser(2, "user", passwordEncode.encode("password"), rolesForUser);

			userRepository.save(user);

		};
	}
}

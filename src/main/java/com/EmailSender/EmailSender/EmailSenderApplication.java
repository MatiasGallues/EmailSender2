package com.EmailSender.EmailSender;

import com.EmailSender.EmailSender.models.ERole;
import com.EmailSender.EmailSender.models.RoleEntity;
import com.EmailSender.EmailSender.models.UserEntity;
import com.EmailSender.EmailSender.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class EmailSenderApplication {

	@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApplication.class, args);
	}




	@Scheduled(cron = "0 0 0 * * ?")
	public void reset(){userRepository.ponerEncero();}

}

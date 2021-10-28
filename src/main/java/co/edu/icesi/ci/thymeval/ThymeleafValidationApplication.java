package co.edu.icesi.ci.thymeval;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;

@SpringBootApplication
public class ThymeleafValidationApplication{

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafValidationApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner cm(UserService userservice) {
		return(args)->{
			UserApp u = new UserApp();
			u.setUsername("admin");
			u.setPassword("123");
			userservice.save(u);
		};
	}

}

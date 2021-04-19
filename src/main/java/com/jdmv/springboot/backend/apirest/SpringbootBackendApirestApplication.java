package com.jdmv.springboot.backend.apirest;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import com.jdmv.springboot.backend.apirest.models.entity.Usuario;

//CommandLineRunner allows to execute a script before running our app
//We're doing this to generate password fields encrypted for out test data 
@SpringBootApplication
public class SpringbootBackendApirestApplication implements CommandLineRunner{
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		for (int i = 0; i < 4; i++) {
//			String passwordCrypt = passwordEncoder.encode("12345");
//			System.out.println(passwordCrypt);
//		}
		
//		Usuario user1 = new Usuario();
//		Usuario user2 = new Usuario();
//		System.out.println(user1.hashCode()+"///"+user2.hashCode());
	}

}

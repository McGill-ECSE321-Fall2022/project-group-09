package ca.mcgill.ecse.mmss;


import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MmssApplication {


	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MmssApplication.class, args);
	}



//	@Bean
//	CommandLineRunner commandLineRunner() {
//		return args -> {
//				Room r1 = new Room();
//				roomRepository.save(r1);
//		};
//	}
}

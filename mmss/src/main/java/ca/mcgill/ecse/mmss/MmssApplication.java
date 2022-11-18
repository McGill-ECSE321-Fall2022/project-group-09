package ca.mcgill.ecse.mmss;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MmssApplication {


//	@Autowired
//	private RoomRepository roomRepository;
//
//	@Autowired
//	private RoomService roomService;

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

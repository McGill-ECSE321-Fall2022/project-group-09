package ca.mcgill.ecse.mmss;


import java.util.ArrayList;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.ManagerService;
import ca.mcgill.ecse.mmss.service.RoomService;
import ca.mcgill.ecse.mmss.service.ScheduleService;
import ca.mcgill.ecse.mmss.service.ShiftService;

@SpringBootApplication
public class MmssApplication {

	@Autowired
	private RoomService roomService;

	@Autowired
	private ManagerService managerService;

	@Autowired ScheduleService scheduleService;
	@Autowired ShiftService shiftService;


	// method to create rooms and manager put it post construct

	/**
	 * Intitialize the application 
	 */
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Manager manager = managerService.getManager(); 
		ArrayList<Room> rooms = roomService.getAllRooms();
		
		try {scheduleService.getSchedule(); } 
		catch (MmssException e) {scheduleService.createSchedule();}

		try {shiftService.createShifts(); } 
		catch (MmssException e) {}
	
		// create manager if he does not exist
		if (manager == null) {
			managerService.createManager(); 
		}

		// create rooms if they do not exist
		if (rooms.size() == 0) {
			roomService.createRooms();
		}



	}

	public static void main(String[] args) {
		SpringApplication.run(MmssApplication.class, args);
	}




}

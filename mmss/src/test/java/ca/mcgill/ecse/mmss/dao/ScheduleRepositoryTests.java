package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Schedule;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScheduleRepositoryTests {
	//repository we are testing
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@AfterEach
	public void clearDatabase() {
		// delete all entries in database
		scheduleRepository.deleteAll();
	}
	
	@Test
	public void testAndPersistAndLoadSchedule() {
		Schedule Schedule = new Schedule();
		
		// save the  schedule
		scheduleRepository.save(Schedule);
		
		// get its id ( that was set automatically by spring )    
	    int ScheduleId = Schedule.getScheduleId(); 
	    
	    // set  schedule to null    
	    Schedule = null;
	    
	    // get Schedule from database using its ID
	    Schedule = scheduleRepository.findScheduleByScheduleId(ScheduleId); 
	    
	    // check primary key and foreign key constraints
	    assertNotNull(Schedule);
        // check an attribute is stored properly
	    assertEquals(ScheduleId, Schedule.getScheduleId()); 
	}
}

package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.WeeklySchedule;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WeeklyScheduleRepositoryTests {
	//repository we are testing
		@Autowired
		private WeeklyScheduleRepository weeklyScheduleRepository;
		
		@AfterEach
		public void clearDatabase() {
			// delete all entries in database
			weeklyScheduleRepository.deleteAll();
		}
		
		@Test
		public void testAndPersistAndLoadWeeklySchedule() {
			WeeklySchedule weeklySchedule = new WeeklySchedule();
			
			// save the weekly schedule
			weeklyScheduleRepository.save(weeklySchedule);
			
			// get its id ( that was set automatically by spring )    
		    int weeklyScheduleId = weeklySchedule.getWeeklyScheduleId(); 
		    
		    // set weekly schedule to null    
		    weeklySchedule = null;
		    
		    // get weeklySchedule from database using its ID
		    weeklySchedule = weeklyScheduleRepository.findWeeklyScheduleByWeeklyScheduleId(weeklyScheduleId); 
		    
		    // check primary key and foriegn key constraints
		    assertNotNull(weeklySchedule);
		    assertEquals(weeklyScheduleId, weeklySchedule.getWeeklyScheduleId()); 
			
		}

}

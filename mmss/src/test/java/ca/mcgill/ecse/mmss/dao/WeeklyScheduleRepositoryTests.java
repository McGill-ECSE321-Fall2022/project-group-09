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

/**
 * @author Athmane
 * Artefact Repository testing class which initiates a room and an artefact repository, executes the tests, then clears these repositories from the database.
 */

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

		  /**
		 * Artefact testing method which creates, populates the attributes, sets associations, and saves the artefact and room objects and identifiers.
		 * It can then test to make sure each object reached from the artefact found in the repository is not null and that each initially saved Id corresponds to the one
		 * reached from the repository.
		 */
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
		    
		    // run J-Unit tests
		    assertNotNull(weeklySchedule);
		    assertEquals(weeklyScheduleId, weeklySchedule.getWeeklyScheduleId()); 
			
		}

}

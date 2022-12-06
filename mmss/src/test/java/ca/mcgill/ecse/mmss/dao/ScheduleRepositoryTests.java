package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.utils.Util;

/**
 * Schedule Repository testing class which initiates a schedule repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScheduleRepositoryTests {
	//repository we are testing
	@Autowired
	private ScheduleRepository scheduleRepository;
      /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }

	@AfterEach
	public void clearDatabase() {
		// delete all entries in database
		scheduleRepository.deleteAll();
	}

		/**
		* Schedule testing method which creates, populates the attributes, sets associations, and saves each schedule object and identifier.
		* It can then test to make sure each object reached from the schedule found in the repository is not null and that each initially saved Id corresponds to the one
		* reached from the repository.
		*/
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
		
		// check primary key and foriegn key constraints
		assertNotNull(Schedule);
		// check an attribute is stored properly
		assertEquals(ScheduleId, Schedule.getScheduleId()); 

			
		}

}

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
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;
import ca.mcgill.ecse.mmss.utils.Util;
import ca.mcgill.ecse.mmss.model.Schedule;

/**
 * Shift Repository testing class which initiates a shift and a schedule repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShiftRepositoryTests {
	 // repository we are testing
	  @Autowired
	  private ShiftRepository shiftRepository; 
	  
	  // also need a  schedule in order to add a shift
	  @Autowired  
	  private ScheduleRepository ScheduleRepository; 
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
	    
	      // make sure the shift is deleted first, because shifts cannot exist without a  schedule
	      shiftRepository.deleteAll();
	      
	      // then you can delete the  schedule
	      ScheduleRepository.deleteAll(); 
	  }

	  /**
	 * Shift testing method which creates, populates the attributes, sets associations, and saves each shift and schedule object and identifier.
	 * It can then test to make sure each object reached from the shift found in the repository is not null and that each initially saved Id corresponds to the one
	 * reached from the repository.
	 */
	  @Test 
	  public void testPersistAndLoadShift() { 
	    
	    // create the  schedule for the shift
	    Schedule Schedule = new Schedule(); 
	    ScheduleRepository.save(Schedule); 
	    
	    // retrieve Id
	    int ScheduleId = Schedule.getScheduleId();
	    
	    // create the shift and populate its fields
	    ShiftTime shiftTime = ShiftTime.Morning;
	    Shift shift = new Shift() ;     
	    shift.setShiftTime(shiftTime); 
	    shift.setSchedule(Schedule);
	    // save the shift    
	    shiftRepository.save(shift); 
	    // get its id ( that was set automatically by spring )    
	    int shiftId = shift.getShiftId();   
	    
	    // set shift and dependency to null    
	    shift = null;
	    Schedule = null;
	    
	    // get the shift from the database using the Id
	    shift = shiftRepository.findShiftByShiftId(shiftId); 
	    
	    // check objects are not null
	    assertNotNull(shift);
	    assertNotNull(shift.getSchedule());
	    
	    // check ids and foreign key constraints
		assertEquals(shiftId, shift.getShiftId());		
		assertEquals(ScheduleId, shift.getSchedule().getScheduleId());
		
	    // check an attribute is stored properly
		assertEquals(shiftTime, shift.getShiftTime()); 
	  }
}

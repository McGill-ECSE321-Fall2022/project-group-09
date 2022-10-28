package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;
import ca.mcgill.ecse.mmss.model.WeeklySchedule;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShiftRepositoryTests {
	 // repository we are testing
	  @Autowired
	  private ShiftRepository shiftRepository; 
	  
	  // also need a weekly schedule in order to add a shift
	  @Autowired  
	  private WeeklyScheduleRepository weeklyScheduleRepository; 
	  
	  @AfterEach
	  public void clearDatabase() {
	    
	      // make sure the shift is deleted first, because shifts cannot exist without a weekly schedule
	      shiftRepository.deleteAll();
	      
	      // then you can delete the weekly schedule
	      weeklyScheduleRepository.deleteAll(); 
	  }

	  @Test 
	  public void testPersistAndLoadShift() { 
	    
	    // create the weekly schedule for the shift
	    WeeklySchedule weeklySchedule = new WeeklySchedule(); 
	    weeklyScheduleRepository.save(weeklySchedule); 
	    
	    // retreive Id
	    int weeklyScheduleId = weeklySchedule.getWeeklyScheduleId();
	    
	    // create the shift and populate its fields
	    ShiftTime shiftTime = ShiftTime.Morning;
	    Shift shift = new Shift() ;     
	    shift.setShiftTime(shiftTime); 
	    shift.setWeeklySchedule(weeklySchedule);
	    // save the shift    
	    shiftRepository.save(shift); 
	    // get its id ( that was set automatically by spring )    
	    int shiftId = shift.getShiftId();   
	    
	    // set shift and dependency to null    
	    shift = null;
	    weeklySchedule = null;
	    
	    // get the shift from the database using the Id
	    shift = shiftRepository.findShiftByShiftId(shiftId); 
	    
	    // check objects are not null
	    assertNotNull(shift);
	    assertNotNull(shift.getWeeklySchedule());
	    
	    // check ids and foriegn key constraints
		assertEquals(shiftId, shift.getShiftId());		
		assertEquals(weeklyScheduleId, shift.getWeeklySchedule().getWeeklyScheduleId());
	    
	  }

}

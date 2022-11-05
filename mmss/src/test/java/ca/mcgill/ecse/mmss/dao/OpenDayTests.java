package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Schedule;

/**
 * Open Day Repository testing class which initiates an open day and a schedule repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OpenDayTests {
  
  // repository we are testing
  @Autowired
  private OpenDayRepository openDayRepository; 
  
  @Autowired 
  private ScheduleRepository weeklyScheduleRepository; 
  
  @AfterEach
  public void clearDatabase() {
      
      // empty openDayRepository
      openDayRepository.deleteAll(); 
      
      // delete the weekly schedule afterwards
      weeklyScheduleRepository.deleteAll();
  }

  /**
 * Open Day testing method which creates, populates the attributes, sets associations, and saves each open day and schedule object and identifier.
 * It can then test to make sure each object reached from the open day found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
  @Test 
  public void testPersistAndLoadManager() { 
    
    // TEST CASE 1: No weekly schedule with open day
	// create OpenDay to be added
	Date date1 = Date.valueOf("2022-10-28");
	OpenDay openDay1 = new OpenDay(date1);
	
	// save the OpenDay to repository
	openDayRepository.save(openDay1);

    // get openDay1 date
	Date openDay1Date = openDay1.getDate();
	
	// TEST CASE 2: Weekly schedule associated with Day
	// create OpenDay to be added
    Date date2 = Date.valueOf("2022-10-29");
    OpenDay openDay2 = new OpenDay(date2);
    
    // create the weekly schedule to add
    Schedule schedule = new Schedule(); 
    weeklyScheduleRepository.save(schedule); 
    openDay2.setSchedule(schedule); 
    
    // save the OpenDay to repository
    openDayRepository.save(openDay1);
    openDayRepository.save(openDay2); 

    // get openDay2 date
    Date openDay2Date = openDay2.getDate(); 
    
	// set the OpenDays to null
	openDay1 = null;
	openDay2 = null; 
	
    // get the OpenDays using the date
    openDay1 = openDayRepository.findOpenDayByDate(openDay1Date); 
    openDay2 = openDayRepository.findOpenDayByDate(openDay2Date); 
    
    // run J-Unit tests
    assertNotNull(openDay1);
    assertNotNull(openDay2);
    // check an attribute is stored properly
    assertEquals(date1, openDay1.getDate());
    assertEquals(date2, openDay2.getDate()); 
  }
}

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

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class OpenDayTests {
  
  // repository we are testing
  @Autowired
  private OpenDayRepository openDayRepository; 
  
  @AfterEach
  public void clearDatabase() {
	  
      // empty openDayRepository
      openDayRepository.deleteAll();
      
  }

  @Test 
  public void testPersistAndLoadManager() { 
    
	// create OpenDay to be added
	Date date = Date.valueOf("2022-10-28");
	OpenDay openDay = new OpenDay(date);
	
	// save the OpenDay to repository
	openDayRepository.save(openDay);

    // get open day date
	Date openDayDate = openDay.getDate();
    
	// set the OpenDay to null
	openDay = null;
	
    // get the OpenDay using the date
    openDay = openDayRepository.findOpenDayByDate(openDayDate); 
    
    // run J-Unit tests
    assertNotNull(openDay);
    assertEquals(date, openDay.getDate());
   
  }
}

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

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Notification;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class NotificationRepositoryTests {
  
  // repository we are testing
  @Autowired
  private NotificationRepository notificationRepository; 
  
  // communication repository
  private CommunicationRepository communicationRepository;
  
  @AfterEach
  public void clearDatabase() {
	  
      // empty notification repository
      notificationRepository.deleteAll(); 
      
      // empty communication repository
      communicationRepository.deleteAll();
      
  }

  @Test 
  public void testPersistAndLoadManager() { 
    
	// create communication that holds notification
	Communication comm = new Communication();
	
	// create Notification to be added
	Notification noti = new Notification();
	String message = "test";
	noti.setMessage(message);
	Date date = new Date(System.currentTimeMillis());
	noti.setDate(date);
	noti.setSentNotification(comm);
	
	// save the notification
	notificationRepository.save(noti);

    // get notification id
	int notiId = noti.getNotificationId();
    
	noti = null;
    // get the manager from the database using the username
    noti = notificationRepository.findNotificationByNotificationId(notiId); 
    
    // run J-Unit tests
    assertNotNull(noti);
    assertEquals(message, noti.getMessage());
    assertEquals(date, noti.getDate());
    assertEquals(comm, noti.getSentNotification());
   
  }
}

package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Notification;
import ca.mcgill.ecse.mmss.utils.Util;

/**
 * Notification Repository testing class which initiates a notification and a communication repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NotificationRepositoryTests {
  
  // repository we are testing
  @Autowired
  private NotificationRepository notificationRepository; 
  
  // communication repository
  @Autowired
  private CommunicationRepository communicationRepository;
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
	  
	  // empty notification repository
	  notificationRepository.deleteAll(); 
	      
	  // empty communication repository
	  communicationRepository.deleteAll();     
  }

  /**
 * Notification testing method which creates, populates the attributes, sets associations, and saves each notification and communication object and identifier.
 * It can then test to make sure each object reached from the notification found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
  @Test 
  public void testPersistAndLoadManager() { 
    
	// create communication that holds notification
	Communication communication = new Communication();
	
	// create Notification to be added
	Notification notification = new Notification();
	String message = "Taco Tuesday is around the corner!";
	notification.setMessage(message);	
	Date date = Date.valueOf("2022-10-28");
	notification.setDate(date);
	notification.setSentNotification(communication);
	
	// save the notification and communication
	communicationRepository.save(communication);
	notificationRepository.save(notification);

    // get notification id
	int notificationId = notification.getNotificationId();
    
	// set notification to null
	notification = null;
	
    // get the notification from the database using the notification ID
    notification = notificationRepository.findNotificationByNotificationId(notificationId); 
    
    // make sure that notification and its communication are not null
    assertNotNull(notification);
    assertNotNull(notification.getSentNotification());
    
    // check the primary keys and foreign key constraints of the notification class
    assertEquals(notificationId, notification.getNotificationId());
    assertEquals(communication.getCommunicationId(), notification.getSentNotification().getCommunicationId());
    
    // check an attribute is stored properly
    assertEquals(message, notification.getMessage()); 
  }
}

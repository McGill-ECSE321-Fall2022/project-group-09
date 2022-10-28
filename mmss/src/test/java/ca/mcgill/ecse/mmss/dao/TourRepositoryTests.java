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
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Room.RoomType;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TourRepositoryTests {
  
  // repository we are testing
  @Autowired
  private TourRepository tourRepository; 
  
  // need open day for tour to exist
  @Autowired
  private OpenDayRepository openDayRepository;
  
  // also need a visitor in order to add a tour
  @Autowired  
  private VisitorRepository visitorRepository; 
  
  //also need a employee in order to add a tour
  @Autowired  
  private EmployeeRepository employeeRepository; 
  
  @Autowired
  private PersonRepository personRepository;
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the tour is deleted first, because tours cannot exist without a employee and visitor
      tourRepository.deleteAll();
      
      // then you can delete all the visitors and employees, open days and people
      openDayRepository.deleteAll();
      visitorRepository.deleteAll(); 
      employeeRepository.deleteAll();
      personRepository.deleteAll();
  }

  @Test 
  public void testPersistAndLoadTour() { 
	// Create a person for the visitor, as visitor cannot exist without person.
	String fNameOne = "Nathaniel";
	String lNameOne = "Relg";
	Person p1 = new Person();
	p1.setFirstName(fNameOne);
	p1.setLastName(lNameOne);
	// save to person repository
	personRepository.save(p1);
	int p1Id = p1.getPersonId();
	
	// Create a person for the visitor, as visitor cannot exist without person.
	String fNameTwo = "Dhimiter";
	String lNameTwo = "Myz";
	Person p2 = new Person();
	p2.setFirstName(fNameTwo);
	p2.setLastName(lNameTwo);
	// save to person repository
	personRepository.save(p2);
	int p2Id = p2.getPersonId();
    
    // create the visitor for the tour
	String username = "nicholas63";
	String password = "password";
	int balance = 230;
    Visitor visitor = new Visitor();
    visitor.setUsername(username); 
    visitor.setPassword(password);
    visitor.setBalance(balance);
    visitor.setPerson(p1);
    // save the visitor 
    visitorRepository.save(visitor); 
    String visitorUname = visitor.getUsername();
    
    // create the employee for the tour
    String usernameTwo = "peterParKer";
    String passwordTwo = "ellamai";
    String phoneNumber = "5145567898";
    Employee employee = new Employee();
    employee.setUsername(usernameTwo);
    employee.setPassword(passwordTwo);
    employee.setPhoneNumber(phoneNumber);
    employee.setPerson(p2);
    // save the employee
    employeeRepository.save(employee);
    String employeeUname = employee.getUsername();
    
    // create Open Day and set its fields for the Tour
    Date tourDate = Date.valueOf("2022-10-27");
    OpenDay openDay = new OpenDay();
    openDay.setDate(tourDate);
    // save the openDay
    openDayRepository.save(openDay);
    Date date = openDay.getDate();
    		
    // create the Tour and populate its fields    
    int numParticipants = 15;
    int bookingPrice = 20;
    Tour.ShiftTime shift = Tour.ShiftTime.Morning;
    Tour tour = new Tour();
    tour.setNumberOfParticipants(numParticipants); 
    tour.setTourTime(shift);
    tour.setPricePerPerson(bookingPrice);
    tour.setTourGuide(employee);
    tour.setVisitor(visitor);
    tour.setDate(openDay);
    // save the tour    
    tourRepository.save(tour);  
    // get its id ( that was set automatically by spring )    
    int bookingId = tour.getBookingId();   
    
    // set tour and dependencies to null 
    employee = null;
    visitor = null;
    openDay = null;
    tour = null;
    
    // get the tour from the database using the Id
    tour = tourRepository.findTourByBookingId(bookingId); 
    
    // run J-Unit tests
    assertNotNull(tour);
    assertEquals(numParticipants, tour.getNumberOfParticipants());
    assertEquals(bookingPrice, tour.getPricePerPerson());
    assertEquals(shift, tour.getTourTime());
	assertEquals(bookingId, tour.getBookingId());

	assertNotNull(tour.getTourGuide());
	assertEquals(employeeUname, tour.getTourGuide().getUsername());

	assertNotNull(tour.getVisitor());
	assertEquals(visitorUname, tour.getVisitor().getUsername());
	
	assertNotNull(tour.getDate());
	assertEquals(date, tour.getDate().getDate());
    
  }
}

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
  
  // visitor needs a person
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
	
	// Create a person for the employee, as employee cannot exist without person.
	String fNameTwo = "Dhimiter";
	String lNameTwo = "Myz";
	Person p2 = new Person();
	p2.setFirstName(fNameTwo);
	p2.setLastName(lNameTwo);
	// save to person repository
	personRepository.save(p2);
	
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
    String visitorUsername = visitor.getUsername();
    
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
    String employeeUsername = employee.getUsername();
    
    // create Open Day and set its fields for the Tour
    Date tourDate = Date.valueOf("2022-10-27");
    OpenDay openDay = new OpenDay();
    openDay.setDate(tourDate);
    // save the openDay
    openDayRepository.save(openDay);
    Date date = openDay.getDate();
    		
    // TEST CASE 1: TOUR HAS OPTIONAL ASSOCIATIONS (IE TOUR WITH TOURGUIDE)
    // create the Tour and populate its fields    
    int numParticipants = 15;
    int bookingPrice = 20;
    Tour.ShiftTime shift = Tour.ShiftTime.Morning;
    Tour tour1 = new Tour();
    tour1.setNumberOfParticipants(numParticipants); 
    tour1.setTourTime(shift);
    tour1.setPricePerPerson(bookingPrice);
    tour1.setTourGuide(employee);
    tour1.setVisitor(visitor);
    tour1.setDate(openDay);
    
    // save the tour    
    tourRepository.save(tour1);  
    // get its id ( that was set automatically by spring )    
    int bookingId1 = tour1.getBookingId();   
    
    // set tour to null
    tour1 = null;
    
    // get the tour from the database using the Id
    tour1 = tourRepository.findTourByBookingId(bookingId1); 
    
    // TEST CASE 2: TOUR DOES NOT HAVE OPTIONAL ASSIGNMENTS ( IE. NO TOURGUIDE)
    
    // create the Tour and Populate its fields    
    Tour tour2 = new Tour();
    tour2.setNumberOfParticipants(numParticipants); 
    tour2.setTourTime(shift);
    tour2.setPricePerPerson(bookingPrice);
    tour2.setVisitor(visitor);
    tour2.setDate(openDay);
    
    // save the tour
    tourRepository.save(tour2); 
    
    // retrieve the bookingId for tour2
    int bookingId2 = tour2.getBookingId(); 
    
    // set tour to null
    tour2 = null; 
    
    // retrieve the tour from the database
    tour2 = tourRepository.findTourByBookingId(bookingId2);
     
    // TEST CASE 1 (TOURGUIDE)
    
    // assert tour and its dependancies are not null 
    assertNotNull(tour1);
    assertNotNull(tour1.getTourGuide());
    assertNotNull(tour1.getVisitor());
    assertNotNull(tour1.getDate());

    // check the primary key and foreign key constraints
	assertEquals(bookingId1, tour1.getBookingId());
	assertEquals(employeeUsername, tour1.getTourGuide().getUsername());
	assertEquals(visitorUsername, tour1.getVisitor().getUsername());
	assertEquals(date, tour1.getDate().getDate());
    

    //TEST CASE 2 (NO TOURGUIDE)
	
    // assert tour and its dependancies are not null
    assertNotNull(tour2);
    assertNotNull(tour2.getVisitor());
    assertNotNull(tour2.getDate());

    // check the primary key and foreign key constraints
    assertEquals(bookingId2, tour2.getBookingId());
    assertEquals(visitorUsername, tour2.getVisitor().getUsername());
    assertEquals(date, tour2.getDate().getDate());
  }
}

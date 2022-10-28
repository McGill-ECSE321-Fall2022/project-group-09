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
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketRepositoryTests {
	
	  // repository we are testing
	  @Autowired
	  private TicketRepository ticketRepository; 

	  // need open day for tour to exist
	  @Autowired
	  private OpenDayRepository openDayRepository;
	  
	  // also need a visitor in order to add a tour
	  @Autowired  
	  private VisitorRepository visitorRepository;  
	  
	  // visitor needs a person
	  @Autowired
	  private PersonRepository personRepository;
	  
	  @AfterEach
	  public void clearDatabase() {
	    
	      // make sure the ticket is deleted first, because ticket cannot exist without a visitor
	      ticketRepository.deleteAll();
	      // then you can delete all the visitors and employees, open days and people
	      visitorRepository.deleteAll();
	      personRepository.deleteAll(); 
	      openDayRepository.deleteAll();
	 
	  }

	  @Test 
	  public void testPersistAndLoadTicket() { 
		// Create a person for the visitor, as visitor cannot exist without person.
		String fNameOne = "Nardo";
		String lNameOne = "Edward";
		Person person = new Person();
		person.setFirstName(fNameOne);
		person.setLastName(lNameOne);
		// save to person repository
		personRepository.save(person);
		
	    // create the visitor for the ticket
		String username = "timGal";
		String password = "password";
		int balance = 150;
	    Visitor visitor = new Visitor();
	    visitor.setUsername(username); 
	    visitor.setPassword(password);
	    visitor.setBalance(balance);
	    visitor.setPerson(person);
	    // save the visitor 
	    visitorRepository.save(visitor); 
	    String visitorUsername = visitor.getUsername();
	    
	    // create Open Day and set its fields for the Ticket
	    Date tourDate = Date.valueOf("2022-10-26");
	    OpenDay openDay = new OpenDay();
	    openDay.setDate(tourDate);
	    // save the openDay
	    openDayRepository.save(openDay);
	    Date date = openDay.getDate();
	    		
	    // create the Ticket and populate its fields    
	    int bookingPrice = 20;
	    Ticket ticket = new Ticket();
	    ticket.setPricePerPerson(bookingPrice);
	    ticket.setVisitor(visitor);
	    ticket.setDate(openDay);
	    // save the ticket   
	    ticketRepository.save(ticket);  
	    // get its id ( that was set automatically by spring )    
	    int bookingId = ticket.getBookingId();   
	    
	    // set ticket and dependencies to null 
	    visitor = null;
	    openDay = null;
	    ticket = null;
	    
	    // get the ticket from the database using the Id
	    ticket = ticketRepository.findTicketByBookingId(bookingId); 
	    
	    // assert retreived objects are not null
	    assertNotNull(ticket);
	    assertNotNull(ticket.getVisitor());
	    assertNotNull(ticket.getDate());

	    
	    // check primary key and foreign key constraints 
		assertEquals(bookingId, ticket.getBookingId());
		assertEquals(visitorUsername, ticket.getVisitor().getUsername());
		assertEquals(date, ticket.getDate().getDate());
	    
	  }

}

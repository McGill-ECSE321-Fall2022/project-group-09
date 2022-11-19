package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private OpenDayRepository openDayRepository;

	@Autowired
	private CommunicationRepository communicationRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	private Person person;
	private Visitor visitor;
	private OpenDay openDay;
	private Communication commmunication;
	private Ticket ticket;

	/**
	 * Create objects needed for all test cases, only modified in DB
	 * 
	 * @author Shyam Desai
	 */
	@BeforeEach
	public void createObjects() {
		this.person = new Person(0, "Jon", "Snow");
		personRepository.save(this.person);

		this.visitor = new Visitor("jon.snow@got.com", "IDontWantIt", person);

		this.openDay = new OpenDay(Date.valueOf("2022-11-15"));
		openDayRepository.save(this.openDay);

		this.commmunication = new Communication();
		visitor.setCommunication(this.commmunication);
		
		communicationRepository.save(this.commmunication);
		visitorRepository.save(this.visitor);

		this.ticket = new Ticket(0, 20, openDay, visitor);
		ticketRepository.save(this.ticket);
	}

	/**
	 * Delete objects after each test
	 * 
	 * @author Shyam Desai
	 */
	@AfterEach
	public void deleteObjects() {
		this.ticket.delete();
		this.visitor.delete();
		this.commmunication.delete();
		this.person.delete();
		this.openDay.delete();
		
		ticketRepository.deleteAll();
		visitorRepository.deleteAll();
		notificationRepository.deleteAll();
		communicationRepository.deleteAll();
		personRepository.deleteAll();
		openDayRepository.deleteAll();
	}

	/**
	 * Test creating and getting a ticket
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateAndGetTicket() {
		int ticketId = testCreateTicket();
		testGetTicket(ticketId);
	}

	/**
	 * Test creating a ticket
	 * 
	 * @return ticket id
	 * @author Shyam Desai
	 */
	public int testCreateTicket() {
		TicketDto request = new TicketDto();
		request.setVisitorUsername("jon.snow@got.com");
		request.setDate(Date.valueOf("2022-11-15"));
		request.setPricePerPerson(20);

		ResponseEntity<TicketDto> response = client.postForEntity("/ticket", request, TicketDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody(), "Response has a body.");
		assertTrue(response.getBody().getBookingId() > 0, "Response has a valid id.");
		return response.getBody().getBookingId();
	}

	/**
	 * Retrieves a ticket that was created by its id
	 * 
	 * @param id
	 * @author Shyam Desai
	 */
	public void testGetTicket(int id) {
		ResponseEntity<TicketDto> response = client.getForEntity("/ticket/" + id, TicketDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody(), "Response has a body.");
		assertEquals(response.getBody().getBookingId(), id, "Response has correct id.");
	}

	/**
	 * Test update date for ticket booking
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTicket() {
		OpenDay updatedOpenDay = new OpenDay(Date.valueOf("2022-11-16")); 
		openDayRepository.save(updatedOpenDay);

		TicketDto ticketDto = new TicketDto(ticket);
		ticketDto.setDate(updatedOpenDay.getDate());

		HttpEntity<TicketDto> request = new HttpEntity<>(ticketDto);

		ResponseEntity<TicketDto> response = client.exchange("/ticket", HttpMethod.PUT, request, TicketDto.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getDate(), Date.valueOf("2022-11-16"));

		Ticket updatedTicket = ticketRepository.findTicketByBookingId(ticket.getBookingId());

		assertEquals(updatedTicket.getDate().getDate(), Date.valueOf("2022-11-16"));
	}

	/**
	 * Test deleting ticket
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testDeleteTicket() {
		TicketDto request = new TicketDto(ticket);
		int id = request.getBookingId();

		ResponseEntity<String> response = client.exchange("/ticket/" + id, HttpMethod.DELETE, null, String.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals("Ticket successfully deleted.", response.getBody());

		Ticket updatedTicket = ticketRepository.findTicketByBookingId(id);

		assertNull(updatedTicket, "Ticket successfully deleted.");

	}

	/**
	 * Test getting all tickets
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTickets() {
		var request = client.getForEntity("/ticket", ArrayList.class);

		ArrayList<TicketDto> extractedTickets = request.getBody();

		assertNotNull(extractedTickets);
		assertEquals(1, extractedTickets.size());
	};

	/**
	 * Test getting all tickets given a date
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByDate() {
		var request = client.getForEntity("/ticket/date?date=2022-11-15", ArrayList.class);

		ArrayList<TicketDto> extractedTickets = request.getBody();

		assertNotNull(extractedTickets);
		assertEquals(1, extractedTickets.size());
	};

	/**
	 * Test getting all tickets given a visitor
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByVisitor() {
		var request = client.getForEntity("/ticket/visitor?username=jon.snow@got.com", ArrayList.class);

		ArrayList<TicketDto> extractedTickets = request.getBody();

		assertNotNull(extractedTickets);
		assertEquals(1, extractedTickets.size());
	};
}
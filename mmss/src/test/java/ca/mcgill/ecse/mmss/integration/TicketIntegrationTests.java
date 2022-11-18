package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.TicketDto;
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

	private Person person;
	private Visitor visitor;
	private OpenDay openDay;
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
		visitorRepository.save(this.visitor);

		this.openDay = new OpenDay(Date.valueOf("2022-11-15"));
		openDayRepository.save(this.openDay);

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
		this.person.delete();
		this.openDay.delete();

		ticketRepository.deleteAll();
		visitorRepository.deleteAll();
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
}

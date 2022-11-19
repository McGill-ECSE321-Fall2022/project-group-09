package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private VisitorRepository visitorRepository;

	@Mock
	private OpenDayRepository openDayRepository;

	@Mock
	private NotificationService notificationService;

	@Mock
	private CommunicationRepository communicationRepository;

	@InjectMocks
	private TicketService ticketService;

	private Person person;
	private Visitor visitor;
	private OpenDay openDay;
	private Communication commmunication;
	private Ticket ticket;

	/**
	 * Create objects needed for all test cases
	 * 
	 * @author Shyam Desai
	 */
	@BeforeEach
	public void createObjects() {
		this.person = new Person(0, "Jon", "Snow");
		this.visitor = new Visitor("jon.snow@got.com", "IDontWantIt", person);
		this.openDay = new OpenDay(new Date(2022 - 11 - 15));
		this.commmunication = new Communication(1);

		visitor.setCommunication(commmunication);

		this.ticket = new Ticket();
		ticket.setVisitor(visitor);
		ticket.setPricePerPerson(20);
		ticket.setBookingId(0);
		ticket.setDate(openDay);
	}

	/**
	 * Delete objects after each test
	 * 
	 * @author Shyam Desai
	 */
	@AfterEach
	public void deleteObjects() {
		this.person.delete();
		this.visitor.delete();
		this.openDay.delete();
		this.commmunication.delete();
		this.ticket.delete();
	}

	/**
	 * Test retrieving a ticket with a valid id
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testRetrieveTicketById() {

		when(ticketRepository.findTicketByBookingId(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> ticket);

		Ticket retrievedTicket = ticketService.retrieveTicketById(0);

		assertEquals(0, retrievedTicket.getBookingId());
		assertEquals(visitor, retrievedTicket.getVisitor());

		verify(ticketRepository, times(1)).findTicketByBookingId(0);
	}

	/**
	 * Test retrieving a ticket with an invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetTicketByInvalidId() {
		final int invalidId = 99;

		when(ticketRepository.findTicketByBookingId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> ticketService.retrieveTicketById(invalidId));

		assertEquals("Ticket not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(ticketRepository, times(1)).findTicketByBookingId(invalidId);
	}

	/**
	 * Test get all tickets
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTickets() {
		ArrayList<Ticket> tickets = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		Ticket ticketTwo = new Ticket(1, 20, new OpenDay(Date.valueOf("2022-11-17")), visitorTwo);

		Ticket ticketThree = new Ticket(2, 20, new OpenDay(Date.valueOf("2022-11-18")), visitorTwo);

		tickets.add(ticket);
		tickets.add(ticketTwo);
		tickets.add(ticketThree);

		when(ticketRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> tickets);

		ArrayList<Ticket> ticketsTest = ticketService.getAllTickets();

		assertEquals(tickets, ticketsTest);

		verify(ticketRepository, times(1)).findAll();
	}

	/**
	 * Test get all tickets given a date
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByDate() {
		ArrayList<Ticket> ticketsForDate = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		OpenDay dateToCheck = new OpenDay(Date.valueOf("2022-11-15"));

		Ticket ticketTwo = new Ticket(1, 20, dateToCheck, visitorTwo);
		Ticket ticketThree = new Ticket(2, 20, new OpenDay(Date.valueOf("2022-11-18")), visitorTwo);

		ticketsForDate.add(ticket);
		ticketsForDate.add(ticketTwo);
		ticketsForDate.add(ticketThree);

		when(openDayRepository.findOpenDayByDate(any(Date.class)))
				.thenAnswer((InvocationOnMock invocation) -> dateToCheck);
		when(ticketRepository.findByDate(any(OpenDay.class)))
				.thenAnswer((InvocationOnMock invocation) -> ticketsForDate);

		ArrayList<Ticket> ticketsTest = ticketService.getAllTicketsByDate(Date.valueOf("2022-11-15"));

		assertEquals(ticketsForDate, ticketsTest);

		verify(openDayRepository, times(1)).findOpenDayByDate(dateToCheck.getDate());
		verify(ticketRepository, times(1)).findByDate(dateToCheck);
	}

	/**
	 * Test get all tickets given an invalid date
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByInvalidDate() {
		OpenDay invalidDate = new OpenDay(Date.valueOf("2022-12-25"));

		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> ticketService.getAllTicketsByDate(invalidDate.getDate()));

		assertEquals("There is no open day on this date.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate.getDate());
	}

	/**
	 * Test get all tickets given a visitor username
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByVisitor() {
		ArrayList<Ticket> ticketsForVisitorTwo = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);

		Ticket ticketTwo = new Ticket();
		ticketTwo.setBookingId(1);
		ticketTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		ticketTwo.setPricePerPerson(20);
		ticketTwo.setVisitor(visitorTwo);

		Ticket ticketThree = new Ticket();
		ticketThree.setBookingId(2);
		ticketThree.setDate(new OpenDay(Date.valueOf("2022-11-18")));
		ticketThree.setPricePerPerson(20);
		ticketThree.setVisitor(visitorTwo);

		ticketsForVisitorTwo.add(ticket);
		ticketsForVisitorTwo.add(ticketTwo);
		ticketsForVisitorTwo.add(ticketThree);

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitorTwo);
		when(ticketRepository.findByVisitor(any(Visitor.class)))
				.thenAnswer((InvocationOnMock invocation) -> ticketsForVisitorTwo);

		ArrayList<Ticket> ticketsTest = ticketService.getAllTicketsByVisitor(visitorTwo.getUsername());

		assertEquals(ticketsForVisitorTwo, ticketsTest);

		verify(visitorRepository, times(1)).findVisitorByUsername("grogu@starwars.com");
		verify(ticketRepository, times(1)).findByVisitor(visitorTwo);
	}

	/**
	 * Test get all tickets given an invalid visitor username
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTicketsByInvalidVisitor() {
		String invalidUsername = "cats@outlook.com";

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> ticketService.getAllTicketsByVisitor(invalidUsername));

		assertEquals("The visitor with this username was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);
	}

	/**
	 * Test creating a ticket with valid parameters
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTicket() {
		OpenDay date = new OpenDay(Date.valueOf("2022-11-16"));

		when(ticketRepository.save(any(Ticket.class)))
				.thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> date);

		Ticket ticketCreated = ticketService.createTicket("jon.snow@got.com", Date.valueOf("2022-11-16"));

		assertEquals("jon.snow@got.com", ticketCreated.getVisitor().getUsername());
		assertEquals(date.getDate(), ticketCreated.getDate().getDate());

		verify(ticketRepository, times(1)).save(any(Ticket.class));
		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(Date.valueOf("2022-11-16"));
	}

	/**
	 * Test creating a ticket with an invalid username
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTicketInvalidUsername() {
		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> ticketService.createTicket("badUsername", Date.valueOf("2022-11-16")));

		assertEquals("The visitor with that username was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("badUsername");
	}

	/**
	 * Test creating a ticket with an invalid date
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTicketInvalidOpenDay() {
		Date invalidDate = Date.valueOf("2022-12-25");

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> ticketService.createTicket("jon.snow@got.com", invalidDate));

		assertEquals("Cannot book tickets on this day.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate);
	}

	/**
	 * Test updating a ticket with a new date, and tests notification feature
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTicket() {
		Date updatedDate = Date.valueOf("2022-11-17");
		OpenDay updatedOpenDay = new OpenDay(updatedDate);

		OpenDay currentDate = new OpenDay(Date.valueOf("2022-11-16"));
		Ticket oldTicket = new Ticket(0, 20, currentDate, visitor);

		when(openDayRepository.findOpenDayByDate(any(Date.class)))
				.thenAnswer((InvocationOnMock invocation) -> updatedOpenDay);
		when(ticketRepository.save(any(Ticket.class)))
				.thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		when(ticketRepository.findTicketByBookingId(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> oldTicket);

		Ticket newTicket = ticketService.updateTicket(0, updatedDate);

		assertEquals(0, newTicket.getBookingId());
		assertEquals(20, newTicket.getPricePerPerson());
		assertEquals("jon.snow@got.com", newTicket.getVisitor().getUsername());
		assertEquals(updatedDate, newTicket.getDate().getDate());

		verify(openDayRepository, times(1)).findOpenDayByDate(updatedDate);
		verify(ticketRepository, times(1)).save(any(Ticket.class));
		verify(ticketRepository, times(1)).findTicketByBookingId(0);

		String message = "Your ticket date change request to " + newTicket.getDate().toString() + "with id: "
				+ String.valueOf(newTicket.getBookingId())
				+ "has been processed! The following email from the Museum will have your updated tickets.";

		verify(notificationService, times(1)).createNotificationByUsername(newTicket.getVisitor().getUsername(),
				message);
	}

	/**
	 * Tests updating a ticket with an invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTicketInvalidId() {
		final int invalidId = 99;

		when(ticketRepository.findTicketByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> ticketService.updateTicket(invalidId, Date.valueOf("2022-11-17")));

		assertEquals("The ticket with this Id was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(ticketRepository, times(1)).findTicketByBookingId(invalidId);
	}

	/**
	 * Tests updating a ticket with an invalid date
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTicketInvalidOpenDay() {
		Date validDate = Date.valueOf("2022-12-25");
		OpenDay validOpenDay = new OpenDay(validDate);
		Ticket validTicket = new Ticket(0, 20, validOpenDay, visitor);

		Date invalidDate = Date.valueOf("2022-12-25");

		when(ticketRepository.findTicketByBookingId(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> validTicket);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> ticketService.updateTicket(0, invalidDate));

		assertEquals("Cannot update tickets to this day.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate);
	}

	/**
	 * Tests deleting a ticket that exists
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testDeleteTicket() {
		Date validDate = Date.valueOf("2022-12-25");
		OpenDay validOpenDay = new OpenDay(validDate);
		Ticket ticketToDelete = new Ticket(0, 20, validOpenDay, visitor);

		when(ticketRepository.findTicketByBookingId(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> ticketToDelete);

		ticketService.deleteTicket(ticketToDelete.getBookingId());

		verify(ticketRepository, times(1)).findTicketByBookingId(ticketToDelete.getBookingId());
	}

	/**
	 * Tests deleting a ticket with an invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testDeleteTicketInvalidId() {
		final int invalidId = 99;

		when(ticketRepository.findTicketByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> ticketService.deleteTicket(invalidId));

		assertEquals("The ticket with this Id was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(ticketRepository, times(1)).findTicketByBookingId(invalidId);
	}
}
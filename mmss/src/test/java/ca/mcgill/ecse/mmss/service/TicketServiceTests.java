package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
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

	@InjectMocks
	private TicketService ticketService;

	private Person person;
	private Visitor visitor;
	private OpenDay openDay;
	private Ticket ticket;

	@BeforeEach
	public void createObjects() {
		this.person = new Person(0, "Jon", "Snow");
		this.visitor = new Visitor("jon.snow@got.com", "IDontWantIt", person);
		this.openDay = new OpenDay(new Date(2022 - 11 - 15));
		this.ticket = new Ticket();
		ticket.setVisitor(visitor);
		ticket.setPricePerPerson(20);
		ticket.setBookingId(0);
		ticket.setDate(openDay);
	}

	@AfterEach
	public void deleteObjects() {
		this.person.delete();
		this.visitor.delete();
		this.ticket.delete();
	}

	@Test
	public void testRetrieveTicketById() {

		when(ticketRepository.findTicketByBookingId(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> ticket);

		Ticket retrievedTicket = ticketService.retrieveTicketById(0);

		assertEquals(0, retrievedTicket.getBookingId());
		assertEquals(visitor, retrievedTicket.getVisitor());

		verify(ticketRepository, times(1)).findTicketByBookingId(0);
	}

	@Test
	public void testGetTicketByInvalidId() {
		final int invalidId = 99;

		when(ticketRepository.findTicketByBookingId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> ticketService.retrieveTicketById(invalidId));

		assertEquals("Ticket not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(ticketRepository, times(1)).findTicketByBookingId(invalidId);
	}

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
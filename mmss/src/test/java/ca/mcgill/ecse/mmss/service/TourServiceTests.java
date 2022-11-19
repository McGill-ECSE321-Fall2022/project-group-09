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
import ca.mcgill.ecse.mmss.dao.TourRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(MockitoExtension.class)
public class TourServiceTests {

	@Mock
	private TourRepository tourRepository;

	@Mock
	private VisitorRepository visitorRepository;

	@Mock
	private OpenDayRepository openDayRepository;

	@Mock
	private NotificationService notificationService;

	@Mock
	private CommunicationRepository communicationRepository;

	@InjectMocks
	private TourService tourService;

	private Person person;
	private Visitor visitor;
	private OpenDay openDay;
	private Communication commmunication;
	private Tour tour;

	/**
	 * Create objects needed for all test cases
	 * 
	 * @author Shyam Desai
	 */
	@BeforeEach
	public void createObjects() {
		this.person = new Person(0, "Jon", "Snow");
		this.visitor = new Visitor("jon.snow@got.com", "IDontWantIt", person);
		this.openDay = new OpenDay(Date.valueOf("2022-11-15"));
		this.commmunication = new Communication(1);

		visitor.setCommunication(commmunication);

		this.tour = new Tour();
		tour.setBookingId(0);
		tour.setVisitor(visitor);
		tour.setPricePerPerson(25);
		tour.setDate(openDay);
		tour.setNumberOfParticipants(5);
		tour.setTourTime(ShiftTime.Morning);
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
		this.tour.delete();
	}

	/**
	 * Test for retrieving tour with valid id
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testRetrieveTourById() {
		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> tour);

		Tour retrievedTour = tourService.retrieveTourById(0);

		assertEquals(0, retrievedTour.getBookingId());
		assertEquals(visitor, retrievedTour.getVisitor());
		assertEquals(25, retrievedTour.getPricePerPerson());
		assertEquals(openDay.getDate(), retrievedTour.getDate().getDate());
		assertEquals(5, retrievedTour.getNumberOfParticipants());
		assertEquals(ShiftTime.Morning, retrievedTour.getTourTime());

		verify(tourRepository, times(1)).findTourByBookingId(0);
	}

	/**
	 * Test for retrieving tour with invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetTourByInvalidId() {
		final int invalidId = 99;

		when(tourRepository.findTourByBookingId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.retrieveTourById(invalidId));

		assertEquals("Tour not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(invalidId);
	}

	/**
	 * Test get all tours
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllTours() {
		ArrayList<Tour> tours = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		Tour tourTwo = new Tour(1, 25, new OpenDay(Date.valueOf("2022-11-17")), 10, ShiftTime.Evening, visitorTwo);

		Tour tourThree = new Tour(2, 25, new OpenDay(Date.valueOf("2022-11-18")), 2, ShiftTime.Afternoon, visitorTwo);

		tours.add(tour);
		tours.add(tourTwo);
		tours.add(tourThree);

		when(tourRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> tours);

		ArrayList<Tour> toursTest = tourService.getAllTours();

		assertEquals(tours, toursTest);

		verify(tourRepository, times(1)).findAll();
	}

	/**
	 * Test get all tours given a date
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByDate() {
		ArrayList<Tour> toursForDate = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		OpenDay dateToCheck = new OpenDay(Date.valueOf("2022-11-15"));

		Tour tourTwo = new Tour(1, 25, dateToCheck, 10, ShiftTime.Evening, visitorTwo);
		Tour tourThree = new Tour(2, 25, new OpenDay(Date.valueOf("2022-11-18")), 2, ShiftTime.Afternoon, visitorTwo);

		toursForDate.add(tour);
		toursForDate.add(tourTwo);
		toursForDate.add(tourThree);

		when(openDayRepository.findOpenDayByDate(any(Date.class)))
				.thenAnswer((InvocationOnMock invocation) -> dateToCheck);
		when(tourRepository.findByDate(any(OpenDay.class))).thenAnswer((InvocationOnMock invocation) -> toursForDate);

		ArrayList<Tour> toursTest = tourService.getAllToursByDate(Date.valueOf("2022-11-15"));

		assertEquals(toursForDate, toursTest);

		verify(openDayRepository, times(1)).findOpenDayByDate(dateToCheck.getDate());
		verify(tourRepository, times(1)).findByDate(dateToCheck);
	}

	/**
	 * Test get all tours given an invalid date
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByInvalidDate() {
		OpenDay invalidDate = new OpenDay(Date.valueOf("2022-12-25"));

		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.getAllToursByDate(invalidDate.getDate()));

		assertEquals("There is no open day with this date.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate.getDate());
	}

	/**
	 * Test get all tours given a visitor username
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByVisitor() {
		ArrayList<Tour> toursForVisitorTwo = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);

		Tour tourTwo = new Tour();
		tourTwo.setBookingId(1);
		tourTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourTwo.setPricePerPerson(25);
		tourTwo.setVisitor(visitorTwo);

		Tour tourThree = new Tour();
		tourThree.setBookingId(2);
		tourThree.setDate(new OpenDay(Date.valueOf("2022-11-18")));
		tourThree.setPricePerPerson(25);
		tourThree.setVisitor(visitorTwo);

		toursForVisitorTwo.add(tour);
		toursForVisitorTwo.add(tourTwo);
		toursForVisitorTwo.add(tourThree);

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitorTwo);
		when(tourRepository.findByVisitor(any(Visitor.class)))
				.thenAnswer((InvocationOnMock invocation) -> toursForVisitorTwo);

		ArrayList<Tour> toursTest = tourService.getAllToursByVisitor(visitorTwo.getUsername());

		assertEquals(toursForVisitorTwo, toursTest);

		verify(visitorRepository, times(1)).findVisitorByUsername("grogu@starwars.com");
		verify(tourRepository, times(1)).findByVisitor(visitorTwo);
	}

	/**
	 * Test get all tours given an invalid visitor username
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByInvalidVisitor() {
		String invalidUsername = "cats@outlook.com";

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.getAllToursByVisitor(invalidUsername));

		assertEquals("The visitor with this username was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);
	}

	/**
	 * Test get all tours with the number of participants less than some number <=
	 * 20
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByNumberOfParticipantsLessThan() {
		ArrayList<Tour> toursForParticipantsLessThan = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);

		Tour tourTwo = new Tour();
		tourTwo.setBookingId(1);
		tourTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourTwo.setPricePerPerson(25);
		tourTwo.setNumberOfParticipants(20);
		tourTwo.setTourTime(ShiftTime.Evening);
		tourTwo.setVisitor(visitorTwo);

		Tour tourThree = new Tour();
		tourThree.setBookingId(2);
		tourThree.setDate(new OpenDay(Date.valueOf("2022-11-18")));
		tourThree.setPricePerPerson(25);
		tourThree.setNumberOfParticipants(8);
		tourThree.setTourTime(ShiftTime.Morning);
		tourThree.setVisitor(visitorTwo);

		toursForParticipantsLessThan.add(tour);
		toursForParticipantsLessThan.add(tourTwo);
		toursForParticipantsLessThan.add(tourThree);

		when(tourRepository.findByNumberOfParticipantsLessThan(any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> toursForParticipantsLessThan);

		ArrayList<Tour> toursTest = tourService.getAllToursByNumberOfParticipantsLessThan(10);

		assertEquals(toursForParticipantsLessThan, toursTest);

		verify(tourRepository, times(1)).findByNumberOfParticipantsLessThan(10);
	}

	/**
	 * Test get all tours with given a tour time
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByShiftTime() {
		ArrayList<Tour> toursForShiftTime = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);

		Tour tourTwo = new Tour();
		tourTwo.setBookingId(1);
		tourTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourTwo.setPricePerPerson(25);
		tourTwo.setNumberOfParticipants(20);
		tourTwo.setTourTime(ShiftTime.Evening);
		tourTwo.setVisitor(visitorTwo);

		Tour tourThree = new Tour();
		tourThree.setBookingId(2);
		tourThree.setDate(new OpenDay(Date.valueOf("2022-11-18")));
		tourThree.setPricePerPerson(25);
		tourThree.setNumberOfParticipants(8);
		tourThree.setTourTime(ShiftTime.Morning);
		tourThree.setVisitor(visitorTwo);

		toursForShiftTime.add(tour);
		toursForShiftTime.add(tourTwo);
		toursForShiftTime.add(tourThree);

		when(tourRepository.findByTourTime(any(ShiftTime.class)))
				.thenAnswer((InvocationOnMock invocation) -> toursForShiftTime);

		ArrayList<Tour> toursTest = tourService.getAllToursByTourTime(ShiftTime.Morning);

		assertEquals(toursForShiftTime, toursTest);

		verify(tourRepository, times(1)).findByTourTime(ShiftTime.Morning);
	}

	/**
	 * Test get all tours given a number of participants and date
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByShiftTimeAndDate() {
		ArrayList<Tour> toursForByShiftTimeAndDate = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		OpenDay openDayToCheck = new OpenDay(Date.valueOf("2022-11-15"));

		Tour tourTwo = new Tour();
		tourTwo.setBookingId(1);
		tourTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourTwo.setPricePerPerson(25);
		tourTwo.setNumberOfParticipants(5);
		tourTwo.setTourTime(ShiftTime.Morning);
		tourTwo.setVisitor(visitorTwo);

		Tour tourThree = new Tour();
		tourThree.setBookingId(2);
		tourThree.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourThree.setPricePerPerson(25);
		tourThree.setNumberOfParticipants(5);
		tourThree.setTourTime(ShiftTime.Evening);
		tourThree.setVisitor(visitorTwo);

		Tour tourFour = new Tour();
		tourFour.setBookingId(2);
		tourFour.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourFour.setPricePerPerson(25);
		tourFour.setNumberOfParticipants(5);
		tourFour.setTourTime(ShiftTime.Morning);
		tourFour.setVisitor(visitorTwo);

		toursForByShiftTimeAndDate.add(tour);
		toursForByShiftTimeAndDate.add(tourTwo);
		toursForByShiftTimeAndDate.add(tourThree);
		toursForByShiftTimeAndDate.add(tourFour);

		when(tourRepository.findByDateAndTourTime(any(OpenDay.class), any(ShiftTime.class)))
				.thenAnswer((InvocationOnMock invocation) -> toursForByShiftTimeAndDate);

		ArrayList<Tour> toursTest = tourService
				.getAllToursByDateAndShiftTime(openDayToCheck, ShiftTime.Morning);

		assertEquals(toursForByShiftTimeAndDate, toursTest);

		verify(tourRepository, times(1)).findByDateAndTourTime(openDayToCheck, ShiftTime.Morning);
	}

	/**
	 * Test get all tours given a number of participants, date, and tour time
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testGetAllToursByShiftTimeAndDateAndNumberOfParticipantsLessThan() {
		ArrayList<Tour> toursForShiftTimeAndDateAndNumberOfParticipantsLessThan = new ArrayList<>();

		Person personTwo = new Person(1, "Grogu", "Mandalorian");
		Visitor visitorTwo = new Visitor("grogu@starwars.com", "BabyYoda", personTwo);
		OpenDay openDayToCheck = new OpenDay(Date.valueOf("2022-11-15"));

		Tour tourTwo = new Tour();
		tourTwo.setBookingId(1);
		tourTwo.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourTwo.setPricePerPerson(25);
		tourTwo.setNumberOfParticipants(15);
		tourTwo.setTourTime(ShiftTime.Morning);
		tourTwo.setVisitor(visitorTwo);

		Tour tourThree = new Tour();
		tourThree.setBookingId(2);
		tourThree.setDate(new OpenDay(Date.valueOf("2022-11-18")));
		tourThree.setPricePerPerson(25);
		tourThree.setNumberOfParticipants(8);
		tourThree.setTourTime(ShiftTime.Morning);
		tourThree.setVisitor(visitorTwo);

		Tour tourFour = new Tour();
		tourFour.setBookingId(2);
		tourFour.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourFour.setPricePerPerson(25);
		tourFour.setNumberOfParticipants(8);
		tourFour.setTourTime(ShiftTime.Evening);
		tourFour.setVisitor(visitorTwo);

		Tour tourFive = new Tour();
		tourFive.setBookingId(2);
		tourFive.setDate(new OpenDay(Date.valueOf("2022-11-15")));
		tourFive.setPricePerPerson(25);
		tourFive.setNumberOfParticipants(14);
		tourFive.setTourTime(ShiftTime.Morning);
		tourFive.setVisitor(visitorTwo);

		toursForShiftTimeAndDateAndNumberOfParticipantsLessThan.add(tour);
		toursForShiftTimeAndDateAndNumberOfParticipantsLessThan.add(tourTwo);
		toursForShiftTimeAndDateAndNumberOfParticipantsLessThan.add(tourThree);
		toursForShiftTimeAndDateAndNumberOfParticipantsLessThan.add(tourFour);
		toursForShiftTimeAndDateAndNumberOfParticipantsLessThan.add(tourFive);

		when(tourRepository.findByTourTimeAndDateAndNumberOfParticipantsLessThan(any(ShiftTime.class),
				any(OpenDay.class), any(int.class)))
				.thenAnswer((InvocationOnMock invocation) -> toursForShiftTimeAndDateAndNumberOfParticipantsLessThan);

		ArrayList<Tour> toursTest = tourService
				.getAllToursByTourTimeAndDateAndNumberOfParticipantsLessThan(ShiftTime.Morning, openDayToCheck, 15);

		assertEquals(toursForShiftTimeAndDateAndNumberOfParticipantsLessThan, toursTest);

		verify(tourRepository, times(1)).findByTourTimeAndDateAndNumberOfParticipantsLessThan(ShiftTime.Morning,
				openDayToCheck, 15);
	}

	/**
	 * Test creating a tour with valid parameters
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTour() {
		OpenDay date = new OpenDay(Date.valueOf("2022-11-15"));

		when(tourRepository.save(any(Tour.class)))
				.thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> date);

		Tour tourCreated = tourService.createTour("jon.snow@got.com", Date.valueOf("2022-11-15"), 5, ShiftTime.Morning);

		assertEquals("jon.snow@got.com", tourCreated.getVisitor().getUsername());
		assertEquals(date.getDate(), tourCreated.getDate().getDate());
		assertEquals(5, tourCreated.getNumberOfParticipants());
		assertEquals(25, tourCreated.getPricePerPerson());
		assertEquals(ShiftTime.Morning, tourCreated.getTourTime());

		verify(tourRepository, times(1)).save(any(Tour.class));
		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(Date.valueOf("2022-11-15"));
	}

	/**
	 * Test creating a tour with an invalid username
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTourInvalidUsername() {
		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.createTour("badUsername", Date.valueOf("2022-11-16"), 25, ShiftTime.Morning));

		assertEquals("The visitor with that username was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("badUsername");
	}

	/**
	 * Test creating a tour with an invalid open day
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTourInvalidOpenDay() {
		Date invalidDate = Date.valueOf("2022-12-25");

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.createTour("jon.snow@got.com", invalidDate, 25, ShiftTime.Morning));

		assertEquals("Cannot book tours on this day.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate);
	}

	/**
	 * Test creating a tour with 0 participants
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTourNoParticipants() {
		final int zeroParticipants = 0;

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.createTour("jon.snow@got.com",
				Date.valueOf("2022-11-15"), zeroParticipants, ShiftTime.Morning));

		assertEquals("Cannot book a tour for 0 visitors.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(openDay.getDate());
	}

	/**
	 * Test creating a tour with too many participants
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testCreateTourTooManyParticipants() {
		final int tooManyParticipants = 21;

		when(visitorRepository.findVisitorByUsername(any(String.class)))
				.thenAnswer((InvocationOnMock invocation) -> visitor);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.createTour("jon.snow@got.com",
				Date.valueOf("2022-11-15"), tooManyParticipants, ShiftTime.Morning));

		assertEquals("Cannot book a tour for more than 20 visitors in this time slot.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(visitorRepository, times(1)).findVisitorByUsername("jon.snow@got.com");
		verify(openDayRepository, times(1)).findOpenDayByDate(openDay.getDate());
	}

	/**
	 * Test updating a tour with new date and number of participants, and tests
	 * notification feature
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTour() {
		OpenDay currentOpenDay = new OpenDay(Date.valueOf("2022-11-15"));
		Tour oldTour = new Tour(0, 25, currentOpenDay, 5, ShiftTime.Morning, visitor);

		OpenDay updatedOpenDay = new OpenDay(Date.valueOf("2022-11-16"));
		final int newNumberOfParticipants = 10;

		when(tourRepository.save(any(Tour.class)))
				.thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		when(openDayRepository.findOpenDayByDate(any(Date.class)))
				.thenAnswer((InvocationOnMock invocation) -> updatedOpenDay);
		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> oldTour);

		Tour updatedTour = tourService.updateTour(0, updatedOpenDay.getDate(), newNumberOfParticipants);

		assertEquals(0, updatedTour.getBookingId());
		assertEquals("jon.snow@got.com", updatedTour.getVisitor().getUsername());
		assertEquals(25, updatedTour.getPricePerPerson());
		assertEquals(updatedOpenDay.getDate(), updatedTour.getDate().getDate());
		assertEquals(newNumberOfParticipants, updatedTour.getNumberOfParticipants());
		assertEquals(ShiftTime.Morning, updatedTour.getTourTime());

		verify(openDayRepository, times(1)).findOpenDayByDate(updatedOpenDay.getDate());
		verify(tourRepository, times(1)).save(any(Tour.class));
		verify(tourRepository, times(1)).findTourByBookingId(0);

		String message = "Your request for tour booking id: " + String.valueOf(updatedTour.getBookingId())
				+ " to modify to" + updatedTour.getDate().toString() + "and " + updatedTour.getNumberOfParticipants()
				+ " participants has been processed! The following email from the Museum will have your updated tour booking.";

		verify(notificationService, times(1)).createNotificationByUsername(updatedTour.getVisitor().getUsername(),
				message);
	}

	/**
	 * Test updating a tour with an invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTourInvalidId() {
		final int invalidId = 99;

		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.updateTour(invalidId, openDay.getDate(), 5));

		assertEquals("The tour with this Id was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(invalidId);
	}

	/**
	 * Test updating a tour with an invalid date
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTourInvalidOpenDay() {
		Date invalidDate = Date.valueOf("2022-12-25");

		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> tour);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.updateTour(0, invalidDate, 5));

		assertEquals("Cannot update tours to this day.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(0);
		verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate);
	}

	/**
	 * Test updating a tour to 0 participants
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTourZeroParticipants() {
		final int newNumberOfParticipants = 0;

		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> tour);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.updateTour(0, openDay.getDate(), newNumberOfParticipants));

		assertEquals("Cannot book a tour for 0 visitors.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(0);
		verify(openDayRepository, times(1)).findOpenDayByDate(openDay.getDate());
	}

	/**
	 * Test updating a tour to more than 20 participants
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testUpdateTourTooManyParticipants() {
		final int newNumberOfParticipants = 21;

		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> tour);
		when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay);

		MmssException ex = assertThrows(MmssException.class,
				() -> tourService.updateTour(0, openDay.getDate(), newNumberOfParticipants));

		assertEquals("Cannot book a tour for more than 20 visitors in this time slot.", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(0);
		verify(openDayRepository, times(1)).findOpenDayByDate(openDay.getDate());
	}

	/**
	 * Test deleting a tour that exists
	 * 
	 * @author Shyam Desai
	 */
	@Test
	public void testDeleteTour() {
		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> tour);

		tourService.deleteTour(tour.getBookingId());

		verify(tourRepository, times(1)).findTourByBookingId(tour.getBookingId());
	}

	/**
	 * Test deleting a tour with an invalid id
	 * 
	 * @throws ex
	 * @author Shyam Desai
	 */
	@Test
	public void testDeleteTourInvalidId() {
		final int invalidId = 99;

		when(tourRepository.findTourByBookingId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);

		MmssException ex = assertThrows(MmssException.class, () -> tourService.deleteTour(invalidId));

		assertEquals("The tour with this Id was not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

		verify(tourRepository, times(1)).findTourByBookingId(invalidId);
	}
}
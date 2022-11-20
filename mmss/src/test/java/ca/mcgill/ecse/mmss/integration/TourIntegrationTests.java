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
import ca.mcgill.ecse.mmss.dao.TourRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.TourDto;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TourIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private TourRepository tourRepository;

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
    private Tour tour;

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

        this.tour = new Tour(0, 25, openDay, 5, ShiftTime.Morning, visitor);
        tourRepository.save(this.tour);
    }

    /**
     * Delete objects after each test
     * 
     * @author Shyam Desai
     */
    @AfterEach
    public void deleteObjects() {
        this.tour.delete();
        this.visitor.delete();
        this.commmunication.delete();
        this.person.delete();
        this.openDay.delete();

        tourRepository.deleteAll();
        visitorRepository.deleteAll();
        notificationRepository.deleteAll();
        communicationRepository.deleteAll();
        personRepository.deleteAll();
        openDayRepository.deleteAll();
    }

    /**
     * Test creating and getting a tour
     * 
     * @author Shyam Desai
     */
    @Test
    public void testCreateAndGetTour() {
        int tourId = testCreateTour();
        testGetTour(tourId);
    }

    /**
     * Test creating a tour
     * 
     * @return tour id
     * @author Shyam Desai
     */
    public int testCreateTour() {
        TourDto request = new TourDto();
        request.setVisitorUsername("jon.snow@got.com");
        request.setDate(Date.valueOf("2022-11-15"));
        request.setNumberOfParticipants(5);
        request.setPricePerPerson(25);
        request.setShiftTime(ShiftTime.Morning);

        ResponseEntity<TourDto> response = client.postForEntity("/tour", request, TourDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body.");
        assertTrue(response.getBody().getBookingId() > 0, "Response has a valid id.");

        return response.getBody().getBookingId();
    }

    /**
     * Retrieves a tour that was created by its id
     * 
     * @param id
     * @author Shyam Desai
     */
    public void testGetTour(int id) {
        ResponseEntity<TourDto> response = client.getForEntity("/tour/" + id, TourDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body.");
        assertEquals(response.getBody().getBookingId(), id, "Response has correct id.");
    }

    /**
     * Test update date for tour booking
     * 
     * @author Shyam Desai
     */
    @Test
    public void testUpdateTour() {
        OpenDay updatedOpenDay = new OpenDay(Date.valueOf("2022-11-16"));
        openDayRepository.save(updatedOpenDay);

        TourDto tourDto = new TourDto(tour);
        tourDto.setDate(updatedOpenDay.getDate());

        HttpEntity<TourDto> request = new HttpEntity<>(tourDto);

        ResponseEntity<TourDto> response = client.exchange("/tour", HttpMethod.PUT, request, TourDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getDate(), Date.valueOf("2022-11-16"));

        Tour updatedTour = tourRepository.findTourByBookingId(tour.getBookingId());

        assertEquals(updatedTour.getDate().getDate(), Date.valueOf("2022-11-16"));
    }

    /**
     * Test deleting tour
     * 
     * @author Shyam Desai
     */
    @Test
    public void testDeleteTour() {
        TourDto request = new TourDto(tour);
        int id = request.getBookingId();

        ResponseEntity<String> response = client.exchange("/tour/" + id, HttpMethod.DELETE, null, String.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("Tour successfully deleted.", response.getBody());

        Tour updatedTour = tourRepository.findTourByBookingId(id);

        assertNull(updatedTour, "Tour successfully deleted.");

    }

    /**
     * Test getting all tours
     * 
     * @author Shyam Desai
     */
    @Test
    public void testGetAllTours() {
        var request = client.getForEntity("/tour", ArrayList.class);

        ArrayList<TourDto> extractedTours = request.getBody();

        assertNotNull(extractedTours);
        assertEquals(1, extractedTours.size());
    };

    /**
     * Test getting all tours given a date
     * 
     * @author Shyam Desai
     */
    @Test
    public void testGetAllToursByDate() {
        var request = client.getForEntity("/tour/date?date=2022-11-15", ArrayList.class);

        ArrayList<TourDto> extractedTours = request.getBody();

        assertNotNull(extractedTours);
        assertEquals(1, extractedTours.size());
    };

    /**
     * Test getting all tours given a visitor's username
     * 
     * @author Shyam Desai
     */
    @Test
    public void testGetAllToursByVisitor() {
        var request = client.getForEntity("/tour/visitor?username=jon.snow@got.com", ArrayList.class);

        ArrayList<TourDto> extractedTours = request.getBody();

        assertNotNull(extractedTours);
        assertEquals(1, extractedTours.size());
    };

    /**
     * Test getting all tours given number of participants
     * 
     * @author Shyam Desai
     */
    @Test
    public void testGetAllToursByParticipantsLessThan() {
        var request = client.getForEntity("/tour/participants?numberOfParticipants=10", ArrayList.class);

        ArrayList<TourDto> extractedTours = request.getBody();

        assertNotNull(extractedTours);
        assertEquals(1, extractedTours.size());
    };

    /**
     * Test getting all tours given a shift time
     * 
     * @author Shyam Desai
     */
    @Test
    public void testGetAllToursByShiftTime() {
        var request = client.getForEntity("/tour/shift?tourTime=Morning", ArrayList.class);

        ArrayList<TourDto> extractedTours = request.getBody();

        assertNotNull(extractedTours);
        assertEquals(1, extractedTours.size());
    };
}
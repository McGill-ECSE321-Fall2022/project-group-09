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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dto.OpenDayDto;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.ArtefactDto;
import ca.mcgill.ecse.mmss.dto.DonationDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Visitor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OpenDayIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    private OpenDayRepository openDayRepository;

    @Autowired
    private ScheduleRepository schedualRepository;

    // Objects that we will need 
    OpenDay openDay;
    Schedule schedule;

    /**
     * Creates the obejcts needed by all test cases. 
     * This is a BeforeEach because objects that are manipulated by certain calls should be reset for each test
     * 
     * @author Mohamed Elsamadouny
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test, and save them to the database
        schedule = new Schedule();
        schedualRepository.save(schedule);

        openDay = new OpenDay(Date.valueOf("2022-10-10"));
        openDay.setSchedule(schedule);
        openDayRepository.save(openDay);
    }

    /**
     * Deletes objects after each test
     * 
     * @author Mohamed Elsamadouny
     */
    @AfterEach
    public void deleteObjects() {

        // delete the objects from the test
        openDay.delete();
        schedule.delete();
        openDayRepository.deleteAll();
        schedualRepository.deleteAll();
    }

    /**
     * Tests creating and retrieving a donation
     *
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateAndGetOpenDay() {
        // create donation dto
        OpenDayDto request = new OpenDayDto(openDay);

        // make the post
        ResponseEntity<OpenDayDto> response1 = client.postForEntity("/openday", request, OpenDayDto.class);
        
        // make assertions on the post
        assertNotNull(response1, "The response is not null");
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertNotNull(response1.getBody(), "Response has a body");
        Date date = response1.getBody().getDate();


        // try the get
        ResponseEntity<OpenDayDto> response2 = client.getForEntity("/openday/2022-10-10", OpenDayDto.class);

        // make assertions on the get
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody(), "Response has a body");
    }
}

package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dto.OpenDayDto;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.utils.Util;


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
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }

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

        // make the post
        ResponseEntity<OpenDayDto> response1 = client.postForEntity("/openday?date=2022-10-10", null, OpenDayDto.class);
        
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

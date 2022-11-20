package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dto.ScheduleDto;
import ca.mcgill.ecse.mmss.model.Schedule;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ScheduleIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ScheduleRepository scheduleRepository;

    Schedule schedule;
    
    /**
     * @author: Athmane Benarous
     * Create the schedule needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        
        // Create schedule
        schedule = new Schedule();
        scheduleRepository.save(schedule);
    }

    /**
     * Delete the schedules and the schedules
     */
    @AfterEach
    public void deleteDelete(){
    	schedule.delete();
        scheduleRepository.deleteAll();
    }

    /**
     * Get the schedule
     */
    @Test
    public void testGetScheduleByScheduleTime() {
        // Try the get
        ResponseEntity<ScheduleDto> response = client.getForEntity("/schedule/", ScheduleDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(schedule.getScheduleId(), response.getBody().getScheduleId());
    }
}

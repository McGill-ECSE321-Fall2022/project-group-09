package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.OpenDayDto;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.service.OpenDayService;

@RestController
@RequestMapping ("/openday")
public class OpenDayController {

    @Autowired
    OpenDayService openDayService;

    /**
     * Get an openDay by date
     * @author Mohamed Elsamadouny
     * 
     * @param date
     * @return a response entity with the donation and ok status
     */
    @GetMapping({"/{date}", "/{date}/"})
    public ResponseEntity<OpenDayDto> getOpenDay(@PathVariable Date date) {
        // call service
        OpenDay retreivedOpenDay = openDayService.getOpenDayByDate(date);
        // return response entity with Dto
        return new ResponseEntity<OpenDayDto>(new OpenDayDto(retreivedOpenDay), HttpStatus.OK);
    }

    /**
     * Create a new OpenDay
     * @author Mohamed Elsamadouny
     * 
     * @param request a OpenDayDTO
     * @return the created OpenDay as a Dto with status ok
     */
    @PostMapping
    public ResponseEntity<OpenDayDto> createOpenDay( @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        // get parameters
        Date newDate = Date.valueOf(date);

        // create the object with the service
        OpenDay retreivedOpenDay = openDayService.createOpenDay(newDate);

        // return it in the response entity
        return new ResponseEntity<OpenDayDto>(new OpenDayDto(retreivedOpenDay), HttpStatus.CREATED);

    }
    
}

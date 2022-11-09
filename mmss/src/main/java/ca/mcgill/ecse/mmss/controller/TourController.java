package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse.mmss.dto.TourDto;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;
import ca.mcgill.ecse.mmss.service.TourService;

@RestController
@RequestMapping ("/tour")
public class TourController {

	@Autowired
	TourService tourService;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author Shyam Desai
	 */
	@GetMapping("/{tour}")
	public ResponseEntity<TourDto> getTour (@PathVariable int id) {
		Tour retrievedTour = tourService.retrieveTourById(id);
        return new ResponseEntity<TourDto>(new TourDto(retrievedTour), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param username
	 * @param date
	 * @param numberOfParticipants
	 * @param tourTime
	 * @return
	 * @author Shyam Desai
	 */
	@PostMapping
    public ResponseEntity<TourDto> createTour (@RequestBody String username, @RequestBody Date date, @RequestBody int numberOfParticipants, @RequestBody ShiftTime tourTime) {
		Tour persistedTour = tourService.createTour(username, date, numberOfParticipants, tourTime);
		return new ResponseEntity<TourDto>(new TourDto(persistedTour), HttpStatus.CREATED);
	}
}
package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse.mmss.dto.TourDto;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;
import ca.mcgill.ecse.mmss.service.TourService;

@RestController
@RequestMapping ("/tour")
public class TourController {

	@Autowired
	TourService tourService;
	
	@GetMapping("/{tour}")
	
	public ResponseEntity<TourDto> getTour (@PathVariable int id) {
        return tourService.retrieveTourById(id).map(tour -> new ResponseEntity<TourDto>(tour, HttpStatus.OK)).orElse(new ResponseEntity<TourDto>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
    public ResponseEntity<TourDto> createTour (@RequestParam String username, @RequestParam Date date, @RequestParam int numberOfParticipants, @RequestParam ShiftTime tourTime) {
		try { 
            TourDto persistedTour = tourService.createTour(username, date, numberOfParticipants, tourTime);
            return new ResponseEntity<TourDto>(persistedTour, HttpStatus.CREATED); // return it in the response entity
        } catch (DataIntegrityViolationException e) { 
            throw e;
        }
	}
}
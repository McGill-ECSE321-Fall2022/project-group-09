package ca.mcgill.ecse.mmss.controller;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.DonationDto;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.service.DonationService;

@RestController
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    DonationService donationService;

    /**
     * Get a donation by its Id
     * @author Mohamed Elsamadouny
     * 
     * @param id
     * @return a response entity with the donation and ok status
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<DonationDto> getLoan(@PathVariable int id) {
        // call service
        Donation retrievedDonation = donationService.getDonationById(id);
        // return response entity with Dto
        return new ResponseEntity<DonationDto>(new DonationDto(retrievedDonation), HttpStatus.OK);
    }

    /**
     * Create a new donation based on an input request
     * @author Mohamed Elsamadouny
     * 
     * @param request a DonationDto
     * @return the created Donation as a Dto with status ok
     */
    @PostMapping
    public ResponseEntity<DonationDto> createLoan(@RequestBody DonationDto request) {
        // get parameters
        String itemName = request.getItemName();
        String username = request.getVisitorUsername();
        String description = request.getDescription();

        // create the object with the service
        Donation persistedDonation = donationService.createDonation(username, itemName, description);
        
        // return it in the response entity
        return new ResponseEntity<DonationDto>(new DonationDto(persistedDonation), HttpStatus.CREATED);

    }


    /**
     * Decline a donation
     * @author Mohamed Elsamadouny
     * 
     * @param request containing the loan id and status
     * @return the updated loan as a Dto, in a response entity, status ok
     */
    @PutMapping
    public ResponseEntity<String> declineDonation(@RequestBody DonationDto request) {
        
        // get parameters
        int loanId = request.getExchangeId();
        ExchangeStatus status = request.getExchangeStatus();

        donationService.declineDonation(loanId, status);

        // return updated Loan as Dto
        return new ResponseEntity<String>("Donation successfully Declined", HttpStatus.OK);

    }

    /**
     * Approve a donation
     * @author Mohamed Elsamadouny
     * 
     * @param request containing the loan id and status
     * @return the updated loan as a Dto, in a response entity, status ok
     */
    @PutMapping
    public ResponseEntity<String> updateDonation(@RequestBody DonationDto request) {
        
        // get parameters
        int donationId = request.getExchangeId();
        ExchangeStatus status = request.getExchangeStatus();

        // call service layer
        // not sure how to get the fees
        donationService.updateStatus(donationId, status, false, 2, 1);

        // return updated Loan as Dto
        return new ResponseEntity<String>("Donation successfully Approved", HttpStatus.OK);

    }

    /**
     * Delete a donation given its id
     * @author Mohamed Elsamadouny
     * 
     * @param request 
     * @return A message saying the donation was deleted
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    public ResponseEntity<String> deleteLoan(@PathVariable int id) {
        // call service layer
        donationService.deleteDonation(id);

        // return updated Loan as Dto
        return new ResponseEntity<String>("Donation successfully deleted", HttpStatus.OK);
    }

    /**
     * Gets all the donations in the system
     * @author Mohamed Elsamadouny
     * 
     * @return an array list with a list of all donations as Dtos
     */
    @GetMapping
    public ResponseEntity<ArrayList<DonationDto>> getAllDonations() {

        // get all donations
        ArrayList<Donation> retrievedDonations = donationService.getAllDonations();

        // make Dtos
        ArrayList<DonationDto> allDonationsDto = new ArrayList<>();
        for (Donation donation : retrievedDonations) {
            allDonationsDto.add(new DonationDto(donation));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<DonationDto>>(allDonationsDto, HttpStatus.OK);

    }

    /**
     * Gets all the donations submitted on a certain day
     * @author Mohamed Elsamadouny
     * 
     * @param date
     * @return an array list with all the donations as Dtos
     */
    @GetMapping({"/submittedDate", "/submittedDate/"})
    public ResponseEntity<ArrayList<DonationDto>> getAllDonationsWithSubmittedDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        // get all donations
        ArrayList<Donation> retrievedDonations = donationService.getAllDonationsBySubmittedDate(Date.valueOf(date));

        // make Dtos
        ArrayList<DonationDto> allDonationsDto = new ArrayList<>();
        for (Donation donation : retrievedDonations) {
            allDonationsDto.add(new DonationDto(donation));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<DonationDto>>(allDonationsDto, HttpStatus.OK);

    }

    /**
     * Gts all the donations in the system with a given status
     * @author Mohamed Elsamadouny
     * 
     * @param status the status
     * @return an array list with all the donations as Dtos
     */
    @GetMapping({"/status", "/status/"})
    public ResponseEntity<ArrayList<DonationDto>> getAllDonationsWithStatus(@RequestParam ExchangeStatus status) {

        // get all donations
        ArrayList<Donation> retrievedDonations = donationService.getAllDonationsByStatus(status);

        // make Dtos
        ArrayList<DonationDto> allDonationsDto = new ArrayList<>();
        for (Donation donation : retrievedDonations) {
            allDonationsDto.add(new DonationDto(donation));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<DonationDto>>(allDonationsDto, HttpStatus.OK);

    }

    /**
     * Gets all the donations belonging to a visitor
     * @author Mohamed Elsamadouny
     * @param username
     * @return an array list with all the donations as Dtos
     */
    @GetMapping({"/visitor","/visitor/"})
    public ResponseEntity<ArrayList<DonationDto>> getAllDonationsWithVisitor(@RequestParam String username) {

        // get all donations
        ArrayList<Donation> retrievedDonations = donationService.getAllDonationsByVisitor(username);

        // make Dtos
        ArrayList<DonationDto> allDonationsDto = new ArrayList<>();
        for (Donation donation : retrievedDonations) {
            allDonationsDto.add(new DonationDto(donation));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<DonationDto>>(allDonationsDto, HttpStatus.OK);

    }
    
}

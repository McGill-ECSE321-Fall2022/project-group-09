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

import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.service.LoanService;

@RestController
@RequestMapping({ "/loan", "/loan/" })
public class LoanController {

    @Autowired
    LoanService loanService;

    /**
     * Get a loan by its Id
     * 
     * @author Shidan Javaheri
     * @param id the int id of the loan
     * @return a response entity with the {@link LoanDto} and the Http status
     */
    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<LoanDto> getLoan(@PathVariable int id) {
        // call service
        Loan retrievedLoan = loanService.getLoanById(id);
        // return response entity with Dto
        return new ResponseEntity<LoanDto>(new LoanDto(retrievedLoan), HttpStatus.OK);
    }

    /**
     * Create a new loan based on an input request
     * 
     * @author Shidan Javaheri
     * @param request {@link LoanDto}, the LoanDto containg the Username of the
     *                visitor and the Id of the artefact that is part of the Loan
     *                request.
     * @return the created {@link LoanDto} in a response entity, with an Http status
     */
    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto request) {

        // get parameters
        int artefactId = request.getArtefactId();
        String username = request.getVisitorId();

        // create the object with the service
        Loan persistedLoan = loanService.createLoan(artefactId, username);

        // return it in the response entity
        return new ResponseEntity<LoanDto>(new LoanDto(persistedLoan), HttpStatus.CREATED);

    }

    /**
     * Update a loan's status to Approved or Declined
     * 
     * @author Shidan Javaheri
     * @param request a {@link LoanDto} containing the loan id and status to update
     *                to
     * @return an updated {@link LoanDto} in a ResponseEntity, with an Http Status
     */
    @PutMapping
    public ResponseEntity<LoanDto> updateLoanStatus(@RequestBody LoanDto request) {
        // get parameters
        int loanId = request.getExchangeId();
        ExchangeStatus status = request.getExchangeStatus();

        // call service layer
        Loan updatedLoan = loanService.updateStatus(loanId, status);

        // return updated Loan as Dto
        return new ResponseEntity<LoanDto>(new LoanDto(updatedLoan), HttpStatus.OK);

    }

    /**
     * Delete a loan given its id
     * 
     * @author Shidan Javaheri
     * @param id the int id of the loan
     * @return A ResponseEntity with a string message saying the loan was deleted,
     *         and an Http status
     */
    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<String> deleteLoan(@PathVariable int id) {
        // call service layer
        loanService.deleteLoan(id);

        // return updated Loan as Dto
        return new ResponseEntity<String>("Loan successfully deleted", HttpStatus.OK);
    }

    // MAPPING OF OTHER GET METHODS

    /**
     * Gets all the loans in the system
     * 
     * @author Shidan Javaheri
     * @return a ResponseEntity with an ArrayList of type {@link LoanDto} and an
     *         Http status
     */
    @GetMapping
    public ResponseEntity<ArrayList<LoanDto>> getAllLoans() {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoans();

        // make Dtos
        ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        for (Loan loan : retrievedLoans) {
            allLoansDto.add(new LoanDto(loan));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<LoanDto>>(allLoansDto, HttpStatus.OK);

    }

    /**
     * Gets all the loans in the system with a given status
     * 
     * @author Shidan Javaheri
     * @param status an ExchangeStatus ( Approved, Pending or Declined )
     * @return a ResponseEntity with an ArrayList of type {@link LoanDto} and an
     *         Http status
     */
    @GetMapping({ "/status", "/status/" })
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithStatus(@RequestParam ExchangeStatus status) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansByStatus(status);

        // make Dtos
        ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        for (Loan loan : retrievedLoans) {
            allLoansDto.add(new LoanDto(loan));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<LoanDto>>(allLoansDto, HttpStatus.OK);

    }

    /**
     * Gets all loans due on a certain day
     * 
     * @author Shidan Javaheri
     * @param date a date
     * @return a ResponseEntity with an ArrayList of type {@link LoanDto} and an
     *         Http status
     */
    @GetMapping({ "/dueDate", "/dueDate/" })
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithDueDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansByDueDate(Date.valueOf(date));

        // make Dtos
        ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        for (Loan loan : retrievedLoans) {
            allLoansDto.add(new LoanDto(loan));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<LoanDto>>(allLoansDto, HttpStatus.OK);

    }

    /**
     * Gets all the loans submitted on a certain day
     * 
     * @author Shidan Javaheri
     * @param date a date
     * @return a ResponseEntity with an ArrayList of type {@link LoanDto} and an
     *         Http status
     */
    @GetMapping({ "/submittedDate", "/submittedDate/" })
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithSubmittedDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansBySubmittedDate(Date.valueOf(date));

        // make Dtos
        ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        for (Loan loan : retrievedLoans) {
            allLoansDto.add(new LoanDto(loan));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<LoanDto>>(allLoansDto, HttpStatus.OK);

    }

    /**
     * Gets all the loans belonging to a visitor
     * 
     * @author Shidan Javaheri
     * @param username the string username of the visitor
     * @return a ResponseEntity with an ArrayList of type {@link LoanDto} and an
     *         Http status
     */
    @GetMapping({ "/visitor", "/visitor/" })
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithVisitor(@RequestParam String username) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansByVisitor(username);

        // make Dtos
        ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        for (Loan loan : retrievedLoans) {
            allLoansDto.add(new LoanDto(loan));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<LoanDto>>(allLoansDto, HttpStatus.OK);

    }

}

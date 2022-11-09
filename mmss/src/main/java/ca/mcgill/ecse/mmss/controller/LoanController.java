package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    /**
     * Get a loan by its Id
     * 
     * @param id
     * @return a response entity with the loan and ok status
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable int id) {
        Loan retrievedLoan = loanService.retrieveLoanById(id);
        return new ResponseEntity<LoanDto>(new LoanDto(retrievedLoan), HttpStatus.OK);
    }

    /**
     * Create a new loan based on an input request
     * 
     * @param request a LoanDto
     * @return the created Loan as a Dto, in a response entity, status ok
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
     * Update a loan's status
     * 
     * @param request containing the loan id and status
     * @return the updated loan as a Dto, in a response entity, status ok
     */
    @PutMapping
    public ResponseEntity<LoanDto> updateLoanStatus(@RequestBody LoanDto request) {
        // get parameters
        int loanId = request.getExchangeId();
        ExchangeStatus status = request.getExchangeStatus();

        // call service layer
        Loan updatedLoan = loanService.updateStatus(loanId, status);

        // return updated Loan as Dto
        return new ResponseEntity<LoanDto>(new LoanDto(updatedLoan), HttpStatus.CREATED);

    }

    /**
     * Delete a loan given its id
     * 
     * @param request the loan's id
     * @return A message saying the loan was deleted
     */
    @DeleteMapping
    public ResponseEntity<String> deleteLoan(@RequestBody LoanDto request) {

        // get required feilds
        int loanId = request.getExchangeId();

        // call service layer
        loanService.deleteLoan(loanId);

        // return updated Loan as Dto
        return new ResponseEntity<String>("Loan succesfully deleted", HttpStatus.CREATED);
    }

    // MAPPING OF OTHER GET METHODS

    /**
     * Gets all the loans in the system
     * 
     * @return an array list with a list of all loans as Dtos
     */
    @GetMapping("/getall")
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
     * Gts all the loans in the system with a given status
     * 
     * @param status the status
     * @return an array list with all the loans as Dtos
     */
    @GetMapping("/getall/status/{status}")
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithStatus(@PathVariable ExchangeStatus status) {

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
     * @param date
     * @return an array list with all the loans as Dtos
     */
    @GetMapping("/getall/dueDate/{date}")
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithDueDate(@PathVariable Date date) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansByDueDate(date);

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
     * @param date
     * @return an array list with all the loans as Dtos
     */
    @GetMapping("/getall/sumbittedDate/{date}")
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithSubmittedDate(@PathVariable Date date) {

        // get all loans
        ArrayList<Loan> retrievedLoans = loanService.getAllLoansBySubmittedDate(date);

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
     * @param username
     * @return an array list with all the loans as Dtos
     */
    @GetMapping("/getall/visitor/{username}")
    public ResponseEntity<ArrayList<LoanDto>> getAllLoansWithStatus(@PathVariable String username) {

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

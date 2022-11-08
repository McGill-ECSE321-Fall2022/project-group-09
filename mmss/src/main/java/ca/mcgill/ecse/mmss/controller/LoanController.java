package ca.mcgill.ecse.mmss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable int id) {
        Loan retrievedLoan = loanService.retrieveLoanById(id);
        return new ResponseEntity<LoanDto>(new LoanDto(retrievedLoan), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody String username, @RequestBody int artefactId) {

        // create the object with the service
        Loan persistedLoan = loanService.createLoan(artefactId, username);

        // return it in the response entity
        return new ResponseEntity<LoanDto>(new LoanDto(persistedLoan), HttpStatus.CREATED);

    }

}

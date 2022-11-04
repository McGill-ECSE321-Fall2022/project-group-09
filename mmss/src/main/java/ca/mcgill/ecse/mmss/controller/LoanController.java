package ca.mcgill.ecse.mmss.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.service.LoanService;

@RestController
public class LoanController {
    @Autowired
    LoanService loanService;

    @GetMapping("/loan/{id}")
    public ResponseEntity<Loan> getLoan (@PathVariable int id) { 
        return loanService.retrieveLoanById(id).map(loan -> new ResponseEntity<Loan>(loan, HttpStatus.OK))
        .orElse(new ResponseEntity<Loan>(HttpStatus.NOT_FOUND));
    }

    // @RequestMapping(method = RequestMethod.POST, path = "/loan"); 
}

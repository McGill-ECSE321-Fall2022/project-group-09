package ca.mcgill.ecse.mmss.controller;

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


import ca.mcgill.ecse.mmss.dto.LoanDto;

import ca.mcgill.ecse.mmss.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;


    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan (@PathVariable int id) { 
        return loanService.retrieveLoanById(id).map(loan -> new ResponseEntity<LoanDto>(loan, HttpStatus.OK))
        .orElse(new ResponseEntity<LoanDto>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan (@RequestParam String username, @RequestParam int artefactId){ 

    
        try { 
        
            // create hte object with the service
            LoanDto persistedLoan = loanService.createLoan(artefactId, username); 

            // return it in the response entity
            return new ResponseEntity<LoanDto>(persistedLoan, HttpStatus.CREATED); 
        } catch (DataIntegrityViolationException e) { 
            throw e;
    
        }

    }
}

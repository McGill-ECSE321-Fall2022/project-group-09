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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;
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
    public ResponseEntity<LoanDto> createLoan (@RequestParam String body){ 

    // use the Json parser
    ObjectMapper mapper = new ObjectMapper();

    try { 
        // parse the json body to get the loan obejct
        Loan loan = mapper.readValue(body, Loan.class); 
        // save it to database
        Loan persistedLoan = loanService.createLoan(loan); 
        LoanDto loanDto = new LoanDto (persistedLoan); 
        // return it in the response entity
        return new ResponseEntity<LoanDto>(loanDto, HttpStatus.CREATED); 
    } catch (JsonProcessingException e) { 
        e.printStackTrace();
    } catch (DataIntegrityViolationException e) { 
        throw e; 
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }




}

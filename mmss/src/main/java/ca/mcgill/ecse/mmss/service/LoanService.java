package ca.mcgill.ecse.mmss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.model.Loan;

@Service
public class LoanService {

    // make the loan repository private
    private LoanRepository loanRepository; 

    // autowire the loan repository with the constructor
    public LoanService (@Autowired LoanRepository loanRepository) {
        this.loanRepository = loanRepository; 
    }

    // what is the difference between findLoanByExchangeId and findById (the return type is optional)
    public Optional<Loan> retrieveLoanById ( int id ) { 
        return loanRepository.findById(id); 
    }

    // should there be a tag here? 
   public Loan createLoan (Loan loan) { 

    // should we be useing try catch statements? 
    try { 
        Loan createdLoan = loanRepository.save(loan); 
        return createdLoan; 

        // what other kinds of exceptions might I need to catch? 
    } catch (DataIntegrityViolationException e) {
        throw e;
    }
   } 
}

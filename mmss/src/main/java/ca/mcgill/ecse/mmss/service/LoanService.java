package ca.mcgill.ecse.mmss.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;

@Service
public class LoanService {

    // make the loan repository private
    private LoanRepository loanRepository; 

    // autowire the loan repository with the constructor
    public LoanService (@Autowired LoanRepository loanRepository) {
        this.loanRepository = loanRepository; 
    }

    /**
     * Finds a loan by its id
     * @author Shidan Javaheri
     * @param id
     * @return the optional loanDto
     */
    public Optional<LoanDto> retrieveLoanById ( int id ) { 
        Loan loan = loanRepository.findLoanByExchangeId(id); 
        LoanDto loanDto = new LoanDto (loan);
        return Optional.of(loanDto); 
    }

    @Transactional
    public Loan createLoan (Loan loan) { 

    try { 
        Loan createdLoan = loanRepository.save(loan); 
        return createdLoan; 

        // what other kinds of exceptions might I need to catch? 
    } catch (DataIntegrityViolationException e) {
        throw e;
    }
   }



}

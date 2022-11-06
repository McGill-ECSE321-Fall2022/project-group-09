package ca.mcgill.ecse.mmss.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Visitor;
@Service
public class LoanService {

    // make the loan repository private
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private VisitorRepository visitorRepository; 

    @Autowired
    private ArtefactRepository artefactRepository; 

    /**
     * Finds a loan by its id
     * @author Shidan Javaheri
     * @param id
     * @return the optional loanDto
     */

    @Transactional
    public Optional<LoanDto> retrieveLoanById ( int id ) { 
        Loan loan = loanRepository.findLoanByExchangeId(id); 
        if (loan == null) {//do something
        }
        var loanDto = new LoanDto (loan);
        return Optional.of(loanDto); 
    }
    /**
     * This method takes in a visitorId, an artefactId, and creates a loan
     * Checks that visitor is able to loan the object
     * Checks that the artefact is available for loan
     * @param artefactId
     * @param visitorId
     * @return a Dto of the created loan
     */
    @Transactional
    public LoanDto createLoan (int artefactId, String username) { 
        // process the inputs, make sure they are valid 
        // tests related to the visitor
        Visitor visitor = visitorRepository.findVisitorByUsername(username); 
        if (visitor == null ) { // do something
        }
        ArrayList<Loan> visitorLoans = loanRepository.findByVisitor(visitor); 
        int length = visitorLoans.size(); 
        if (visitor.getBalance() != 0 ) { 
            // do something
        } else if (visitorLoans.size() > 5 ) { 
            // do something
        } else { 
            for (int i=0; i<length; i++) { 
                // check that the visitors loans are not out of date
            }
        }

        // tests related to the artefact

            
        // if all checks pass, do this. Otherwise it will have returned an exception
        Loan loan = new Loan (); 
        loan.setArtefact(artefactRepository.findArtefactByArtefactId(artefactId)); 
        loan.setVisitor(visitor); 

        // save the new loan object 
        loanRepository.save(loan); 

        // return a Dto of the created loan object
        return (new LoanDto (loan)); 

    }

}

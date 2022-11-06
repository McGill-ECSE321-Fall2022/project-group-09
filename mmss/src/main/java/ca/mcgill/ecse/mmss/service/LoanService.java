package ca.mcgill.ecse.mmss.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

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
     * 
     * @author Shidan Javaheri
     * @param id
     * @return the optional loanDto
     */

    @Transactional
    public Optional<LoanDto> retrieveLoanById(int id) {
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {// do something
        }
        var loanDto = new LoanDto(loan);
        return Optional.of(loanDto);
    }

    @Transactional
    public ArrayList<LoanDto> getAllLoans() { 
        ArrayList<Loan> allLoans = loanRepository.findAll(); 
        for ()

    }

    /**
     * This method takes in a visitorId, an artefactId, and creates a loan
     * Checks that visitor is able to loan the object
     * Checks that the artefact is available for loan
     * 
     * @param artefactId
     * @param visitorId
     * @return a Dto of the created loan
     */
    @Transactional
    public LoanDto createLoan(int artefactId, String username) {
        // process the inputs, make sure they are valid
        String error = "";

        // tests related to the visitor
        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null) {
            error += "The visitor cannot be null.";
        } else {
            ArrayList<Loan> visitorLoans = loanRepository.findByVisitor(visitor);
            int length = visitorLoans.size();
            double balance = visitor.getBalance();
            if (balance != 0) {
                error += "You cannot loan an item until your outstanding balances are paid.";
            } else if (visitorLoans.size() > 5) {
                error += "You cannot loan more than 5 items at a time";
            } else {
                for (int i = 0; i < length; i++) {
                    Date date = new Date(System.currentTimeMillis());
                    Loan aLoan = visitorLoans.get(i);
                    if (aLoan.getExchangeStatus() == ExchangeStatus.Approved) {
                        // nested condition because only approved loans have due dates
                        if (aLoan.getDueDate().getDate().compareTo(date) > 0) {
                            error += "Please return outstanding loaned items before loaning a new one";
                            break;
                        }
                    }
                }
            }
        }

        // tests related to the artefact
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);
        if (artefact == null) {
            error += "The artefact cannot be null";
        } else {
            boolean canLoan = artefact.getCanLoan();
            boolean currentlyOnLoan = artefact.getCurrentlyOnLoan();
            if (!canLoan && currentlyOnLoan)
                error += "This item is unavailable for loan";
        }

        // if all checks pass, do this. Otherwise it will have returned an exception
        if (error == "") {

            Loan loan = new Loan();
            loan.setArtefact(artefactRepository.findArtefactByArtefactId(artefactId));
            loan.setVisitor(visitor);
            loan.setSubmittedDate(new Date(System.currentTimeMillis()));

            // save the new loan object
            loanRepository.save(loan);

            // return a Dto of the created loan object
            return (new LoanDto(loan));

        } else {
            throw new IllegalArgumentException(error);
        }

    }

    @Transactional
    public void deleteLoan(int id) {
        String error = "";
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null)
            error += "Loan not found";
        if (error == "") {
            loanRepository.delete(loan);
        } else
            throw new IllegalArgumentException(error);

    }

    @Transactional
    public LoanDto updateStatus(int id, ExchangeStatus status) {
        String error = "";
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {
            error += "Loan not found";
        } else { 
            if (status == ExchangeStatus.Pending) {
                 error += "Cannot set the loans status to pending"; 
                } else if (status == ExchangeStatus.Declined) { 
                    deleteLoan(loan.getExchangeId()); 
                    //Notification notification = new Notification()
                } else if (status == ExchangeStatus.Approved) { 


                }
            
        }
        return null; 
    }
}


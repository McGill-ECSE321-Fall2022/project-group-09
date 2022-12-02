package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.sql.Date;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

/**
 * Business logic for the Loan class
 * 
 * @author Shidan Javaheri
 */
@Service
public class LoanService {

    // autowire necessary repositories
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private OpenDayRepository openDayRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OpenDayService openDayService;

    /**
     * Finds a loan by its id
     * 
     * @author Shidan Javaheri
     * @param id the id of the loan
     * @return the loan object
     * @throws MmssException if the loan with that Id is not found
     */
    @Transactional
    public Loan getLoanById(int id) {

        // use the repository method
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Loan not found");
        }
        return loan;
    }

    /**
     * 
     * Finds all the loans in the database
     * 
     * @author Shidan Javaheri
     * @return an ArrayList of Loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoans() {

        // use repository method
        ArrayList<Loan> allLoans = loanRepository.findAll();

        return allLoans;
    }

    /**
     * Finds all the loans in the database with a given status
     * 
     * @author Shidan Javaheri
     * @param status the ExchangeStatus in question
     * @return an ArrayList of Loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByStatus(ExchangeStatus status) {

        // use repository method
        ArrayList<Loan> allLoans = loanRepository.findByExchangeStatus(status);

        return allLoans;

    }

    /**
     * Gets all the loans by their due date
     * 
     * @author Shidan Javaheri
     * 
     * @param dueDate a date
     * @return an ArrayList of Loans
     * @throws MmssException if the OpenDay is not found
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByDueDate(Date dueDate) {

        OpenDay openDayDue = openDayRepository.findOpenDayByDate(dueDate);
        if (openDayDue == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "There is no open day with this date");
        }
        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findByDueDate(openDayDue);

        return allLoans;

    }

    /**
     * Gets all the loans based on the day they were submitted
     * 
     * @author Shidan Javaheri
     * @param submittedDate a date
     * @return an array list of LoanDtos
     */
    @Transactional
    public ArrayList<Loan> getAllLoansBySubmittedDate(Date submittedDate) {

        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findBySubmittedDate(submittedDate);

        return allLoans;
    }

    /**
     * Gets all the loans associated with a particular visitor
     * 
     * @author Shidan Javaheri
     * @param username the String username of the Visitor
     * @return an ArrayList of Loans
     * @throws MmssException if the visitor is not found
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found");
        }

        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findByVisitor(visitor);

        return allLoans;
    }

    /**
     * This method takes in a visitorId, an artefactId, and creates a loan.
     * It checks that both the visitor and artefact exist
     * It checks that visitor is able to loan the object
     * It checks that the artefact is available for loan
     * 
     * @author Shidan Javaheri
     * @param artefactId the id of the artefact to take on Loan
     * @param username   the username of the visitor requesting the Loan
     * @return the created Loan object
     * @throws MmssException if creating a Loan is not possible
     */
    @Transactional
    public Loan createLoan(int artefactId, String username) {

        // tests related to the visitor
        Visitor visitor = visitorRepository.findVisitorByUsername(username);

        // check visitor not null
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found");
        } else {

            // visitor is not null, so get all their loans and their balance for further
            // checks
            ArrayList<Loan> visitorLoans = loanRepository.findByVisitor(visitor);
            int length;
            if (visitorLoans != null) {
                length = visitorLoans.size();
            } else
                length = 0;
            double balance = visitor.getBalance();

            // check for non zero balance
            if (balance != 0) {
                throw new MmssException(HttpStatus.BAD_REQUEST,
                        "You cannot loan items when you have an outstanding balance");
            }
            // check for more than 5 loans currently
            if (length > 5) {
                throw new MmssException(HttpStatus.BAD_REQUEST, "You cannot loan more than 5 items at a time");
            }
            // check for outstanding loans
            if (visitorLoans != null) {
                for (int i = 0; i < length; i++) {
                    Date currentDate = new Date(System.currentTimeMillis());

                    Loan aLoan = visitorLoans.get(i);

                    // if the loan is approved ie. has a due date
                    if (aLoan.getExchangeStatus() == ExchangeStatus.Approved) {

                        // nested condition because only approved loans have due dates
                        if (aLoan.getDueDate().getDate().compareTo(currentDate) > 0) {
                            throw new MmssException(HttpStatus.BAD_REQUEST,
                                    "Please return outstanding loaned items before loaning a new one");

                        }
                    }
                }
            }

        }

        // tests related to the artefact
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);

        // null check
        if (artefact == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact with this Id was not found");

            // availability check
        } else {
            boolean canLoan = artefact.getCanLoan();
            boolean currentlyOnLoan = artefact.getCurrentlyOnLoan();
            if (!canLoan || currentlyOnLoan)
                throw new MmssException(HttpStatus.BAD_REQUEST, "This item is not available to be loaned");
        }

        // By now, all checks should have passed. Otherwise it will have returned an
        // exception
        Loan loan = new Loan();
        loan.setArtefact(artefact);
        loan.setVisitor(visitor);
        loan.setSubmittedDate(new Date(System.currentTimeMillis()));
        loan.setExchangeStatus(ExchangeStatus.Pending);

        // save the new loan object
        loanRepository.save(loan);

        // save the artefact with new loan status
        artefact.setCurrentlyOnLoan(true);
        artefactRepository.save(artefact);

        // return a Dto of the created loan object
        return (loan);

    }

    /**
     * Deletes the loan of a given id if the loan exits
     * 
     * @author Shidan Javaheri
     * @param id the id of the loan to be deleted
     * @throws MmssException if the loan with that Id does not exist
     */
    @Transactional
    public void deleteLoan(int id) {

        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The loan with this Id was not found");

        // set the artefact to currently not on loan
        Artefact artefact = loan.getArtefact();
        artefact.setCurrentlyOnLoan(false);
        artefactRepository.save(artefact);

        // calls the repository to delete the loan
        loanRepository.deleteById(loan.getExchangeId());
    }

    /**
     * Takes in the id of a loan and a status to modify its status
     * Declined loans are immediately delted
     * 
     * @author Shidan Javaheri
     * @param id
     * @param status
     * @return the loan object
     * @throws MmssException if the input arguments are invalid 
     */
    @Transactional
    public Loan updateStatus(int id, ExchangeStatus status) {

        // get the loan if it exists
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The loan with this Id was not found");
        } else {
            // can't set status to pending
            if (status == ExchangeStatus.Pending) {
                throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot set the status of a loan to pending");
                
                // declined loans are deleted immediately
            } else if (status == ExchangeStatus.Declined) {

                loan.setExchangeStatus(ExchangeStatus.Declined);
                
                // make artefact available again
                Artefact artefact = loan.getArtefact();
                artefact.setCurrentlyOnLoan(false);
                artefactRepository.save(artefact);

                deleteLoan(loan.getExchangeId());

                // create a notification with this message, attached to this visitor
                String message = "Your loan request submitted on date" + loan.getSubmittedDate().toString()
                        + "with id: " + String.valueOf(loan.getExchangeId()) + "has been denied";

                // use create notification method from Sasha
                notificationService.createNotificationByUsername(loan.getVisitor().getUsername(), message);

                // approvedloans also set the due date of the loans to 7 days form now
            } else if (status == ExchangeStatus.Approved) {
                loan.setExchangeStatus(status);

                // find open day 7 days from current date
                OpenDay dueDate = openDayService.calculateLoanDueDate(new Date(System.currentTimeMillis()));
                loan.setDueDate(dueDate);

                loanRepository.save(loan);
                artefactRepository.save(loan.getArtefact());
                // create notification message
                String message = "Your loan request submitted on date" + loan.getSubmittedDate().toString()
                        + "with id: " + String.valueOf(loan.getExchangeId())
                        + "has been approved! Please follow this link to process payment, and pass by the Museum to pick up the related artefact. http://payhere.com";
                // use create notification method from Sasha
                notificationService.createNotificationByUsername(loan.getVisitor().getUsername(), message);
            }

        }
        return loan;
    }
}


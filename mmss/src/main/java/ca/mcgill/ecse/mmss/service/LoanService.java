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
    OpenDayRepository openDayRepository;

    /**
     * Finds a loan by its id
     * 
     * @author Shidan Javaheri
     * @param id
     * @return the loan, or throw an exception that the loan was not found
     */
    @Transactional
    public Loan retrieveLoanById(int id) {
        // use the repository method
        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Loan not found");
        }
        return loan;
    }

    /**
     * Finds all the loans in the database
     * 
     * @return an arraylist of loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoans() {

        // use repository method
        ArrayList<Loan> allLoans = loanRepository.findAll();

        // I have kept the convert to Dto logic to move it to the controller
        // ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        // for (Loan loan : allLoans) {
        // allLoansDto.add(new LoanDto(loan));
        // }
        return allLoans;
    }

    /**
     * Finds all the loans in the database with a given status
     * 
     * @param status
     * @return an array list of loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByStatus(ExchangeStatus status) {

        // use repository method
        ArrayList<Loan> allLoans = loanRepository.findByExchangeStatus(status);

        // I have kept the convert to Dto logic to move it to the controller

        // ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        // for (Loan loan : allLoans) {
        // allLoansDto.add(new LoanDto(loan));
        // }
        return allLoans;

    }

    /**
     * Gets all the loans by their due date
     * 
     * @param dueDate
     * @return an array list of Loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByDueDate(Date dueDate) {

        OpenDay openDayDue = openDayRepository.findOpenDayByDate(dueDate);
        if (openDayDue == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "There is no open day with this due date");
        }
        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findByDueDate(openDayDue);

        // move to controller
        // ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        // for (Loan loan : allLoans) {
        // allLoansDto.add(new LoanDto(loan));
        // }
        return allLoans;

    }

    /**
     * Gets all the loans based on the day they were submitted
     * 
     * @param submittedDate
     * @return an array list of LoanDtos
     */
    @Transactional
    public ArrayList<Loan> getAllLoansBySubmittedDate(Date submittedDate) {

        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findBySubmittedDate(submittedDate);

        // I have kept the convert to Dto logic to move it to the controller
        // ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        // for (Loan loan : allLoans) {
        // allLoansDto.add(new LoanDto(loan));
        // }
        return allLoans;
    }

    /**
     * Gets all the loans associated with a particular visitor
     * 
     * @param username
     * @return an array list of Loans
     */
    @Transactional
    public ArrayList<Loan> getAllLoansByVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this Id was not found");
        }
        // use the repository
        ArrayList<Loan> allLoans = loanRepository.findByVisitor(visitor);

        // I have kept the convert to Dto logic to move it to the controller
        // ArrayList<LoanDto> allLoansDto = new ArrayList<>();
        // for (Loan loan : allLoans) {
        // allLoansDto.add(new LoanDto(loan));
        // }
        return allLoans;
    }

    /**
     * This method takes in a visitorId, an artefactId, and creates a loan
     * Checks that visitor is able to loan the object
     * Checks that the artefact is available for loan
     * 
     * @param artefactId
     * @param visitorId
     * @return the created loan
     */
    @Transactional
    public Loan createLoan(int artefactId, String username) {

        // tests related to the visitor
        Visitor visitor = visitorRepository.findVisitorByUsername(username);

        // check visitor not null
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this Id was not found");
        } else {

            // not null, so get all their loans and their balance for further checks
            ArrayList<Loan> visitorLoans = loanRepository.findByVisitor(visitor);
            int length = visitorLoans.size();
            double balance = visitor.getBalance();

            // non zero balance
            if (balance != 0) {
                throw new MmssException(HttpStatus.BAD_REQUEST,
                        "You cannot loan items when you have an outstanding balance");
            }
            // more than 5 loan currently
            if (visitorLoans.size() > 5) {
                throw new MmssException(HttpStatus.BAD_REQUEST, "You cannot loan more than 5 items at a time");
            }
            // outstanding loans
            for (int i = 0; i < length; i++) {
                Date date = new Date(System.currentTimeMillis());
                Loan aLoan = visitorLoans.get(i);
                // if the loan is approved ie. has a due date
                if (aLoan.getExchangeStatus() == ExchangeStatus.Approved) {
                    // nested condition because only approved loans have due dates
                    if (aLoan.getDueDate().getDate().compareTo(date) > 0) {
                        throw new MmssException(HttpStatus.BAD_REQUEST,
                                "Please return outstanding loaned items before loaning a new one");

                    }
                }
            }

        }

        // tests related to the artefact
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);
        if (artefact == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact with this Id was not found");
        } else {
            boolean canLoan = artefact.getCanLoan();
            boolean currentlyOnLoan = artefact.getCurrentlyOnLoan();
            if (!canLoan && currentlyOnLoan)
                throw new MmssException(HttpStatus.BAD_REQUEST, "This item is not available to be loaned");
        }

        // By now, all checks should have passed. Otherwise it will have returned an
        // exception
        Loan loan = new Loan();
        loan.setArtefact(artefact);
        loan.setVisitor(visitor);
        loan.setSubmittedDate(new Date(System.currentTimeMillis()));

        // save the new loan object
        loanRepository.save(loan);

        // return a Dto of the created loan object
        return (loan);

    }

    /**
     * Deletes the loan of a given id if the loan exits
     * 
     * @param id
     */
    @Transactional
    public void deleteLoan(int id) {

        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The loan with this Id was not found");

        // calls the repository to delete the loan
        loanRepository.delete(loan);
    }

    /**
     * Takes in the id of a loan and a status to modify its status
     * Declined loans are immediately delted
     * 
     * @param id
     * @param status
     * @return
     */
    @Transactional
    public Loan updateStatus(int id, ExchangeStatus status) {

        Loan loan = loanRepository.findLoanByExchangeId(id);
        if (loan == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The loan with this Id was not found");
        } else {
            if (status == ExchangeStatus.Pending) {
                throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot set the status of a loan to pending");
            } else if (status == ExchangeStatus.Declined) {
                deleteLoan(loan.getExchangeId());
                // could add a notification that is sent
                // Notification notification = new Notification();
                // notification.setMessage("Your loan request submitted on date",
                // loan.getSubmittedDate().toString(), "with id: " , loan.getExchangeId().to,
                // "has been denied");

            } else if (status == ExchangeStatus.Approved) {
                loan.setExchangeStatus(status);
                // Need method from Mohammed
                // OpenDay dueDate = use open day method to find 7 days from now
                // loan.setDueDate(openDay);
                loanRepository.save(loan);

                // could send a notfication that says its approved and asks for payment
            }

        }
        return loan;
    }
}

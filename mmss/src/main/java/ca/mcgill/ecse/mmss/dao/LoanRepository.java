package ca.mcgill.ecse.mmss.dao;


import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

public interface LoanRepository extends CrudRepository<Loan, Integer> {

    Loan findLoanByExchangeId(int exchangeId);
    ArrayList<Loan> findByExchangeStatus (ExchangeStatus status);
    ArrayList<Loan> findByDueDate (Date dueDate); 
    ArrayList<Loan> findByVisitor (Visitor visitor); 
    ArrayList<Loan> findBySubmittedDate (Date date); 






}
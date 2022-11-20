package ca.mcgill.ecse.mmss.dao;

import java.sql.Date;
import java.util.ArrayList;

import ca.mcgill.ecse.mmss.model.Artefact;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;


public interface LoanRepository extends CrudRepository<Loan, Integer> {

    Loan findLoanByExchangeId(int exchangeId);
    ArrayList<Loan> findAllByArtefact(Artefact artefact);

    ArrayList<Loan> findByExchangeStatus(ExchangeStatus status);

    ArrayList<Loan> findByDueDate(OpenDay dueDate);

    ArrayList<Loan> findByVisitor(Visitor visitor);

    ArrayList<Loan> findBySubmittedDate(Date date);

    ArrayList<Loan> findAll();

}
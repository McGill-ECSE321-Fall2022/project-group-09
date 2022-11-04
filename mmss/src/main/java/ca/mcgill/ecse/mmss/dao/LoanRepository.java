package ca.mcgill.ecse.mmss.dao;


import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Integer> {

    /**
     * Finds a loan by its exchange Id
     * @author Sasha Denouvilliez-Pesh
     * @param exchangeId
     * @return the loan instance
     */
    Loan findLoanByExchangeId(int exchangeId);




}
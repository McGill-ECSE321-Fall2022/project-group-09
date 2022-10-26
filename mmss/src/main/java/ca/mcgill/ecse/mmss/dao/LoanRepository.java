package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
    Loan findLoanByExchangeId(int exchangeId);
}
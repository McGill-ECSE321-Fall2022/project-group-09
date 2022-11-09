package ca.mcgill.ecse.mmss.dao;


import ca.mcgill.ecse.mmss.model.Artefact;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Loan;

import java.util.ArrayList;

public interface LoanRepository extends CrudRepository<Loan, Integer> {

    /**
     * Finds a loan by its exchange Id
     * @author Sasha Denouvilliez-Pesh
     * @param exchangeId
     * @return the loan instance
     */
    Loan findLoanByExchangeId(int exchangeId);
    ArrayList<Loan> findAllByArtefact(Artefact artefact);




}
package ca.mcgill.ecse.mmss.dao;
import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;


public interface DonationRepository extends CrudRepository<Donation, Integer> {
    Donation findDonationByExchangeId(int exchangeId);

	ArrayList<Donation> findByVisitor(Visitor visitor);

	ArrayList<Donation> findBySubmittedDate(Date submittedDate);

	ArrayList<Donation> findByExchangeStatus(ExchangeStatus status);
	
	ArrayList<Donation> findAll();

}
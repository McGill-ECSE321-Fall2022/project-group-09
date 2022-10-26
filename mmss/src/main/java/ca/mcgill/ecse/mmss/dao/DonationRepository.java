package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Donation;

public interface DonationRepository extends CrudRepository<Donation, Integer> {
    Donation findDonationByExchangeId(int exchangeId);
}
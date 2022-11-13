package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

@Service
public class DonationService {

	// make the donation repository private
	@Autowired
	private DonationRepository donationRepository;

	@Autowired
	private VisitorRepository visitorRepository;
	
	/**
	 * @author Mohamed Elsamadouny
	 * Finds donation by id
	 * @param id
	 * @return the donation, throws an exception if the donation was not found
	 */

	@Transactional
	public Donation retrieveDonationById(int id) {
		// use the repository method
		Donation donation = donationRepository.findDonationByExchangeId(id);
		if (donation == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Loan not found");
		}
		return donation;
	}
	
	/**
	 * @author Mohamed Elsamadouny
	 * Finds all the donations in the database
	 *
	 * @return ArrayList of Donations
	 */
	
	@Transactional
    public ArrayList<Donation> getAllDonations() {

        // use repository method
        ArrayList<Donation> allDonations = donationRepository.findAll();

        return allDonations;
    }
	
	/**
	 * @author Mohamed Elsamadouny
	 * Finds all the donations submitted on a certain day
	 *
	 * @param the submittedDate
	 * @return ArrayList of Donations
	 */
	
	@Transactional
    public ArrayList<Donation> getAllLoansBySubmittedDate(Date submittedDate) {

        // use the repository
        ArrayList<Donation> allDonations = donationRepository.findBySubmittedDate(submittedDate);

        return allDonations;
    }
	
	/**
	 * @author Mohamed Elsamadouny
	 * Finds all the donations submitted by a certain visitor
	 *
	 * @param the visitors username
	 * @return ArrayList of Donations
	 */
	
    @Transactional
    public ArrayList<Donation> getAllLoansByVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this Id was not found");
        }

        // use the repository
        ArrayList<Donation> allDonations = donationRepository.findByVisitor(visitor);

        return allDonations;
    }
    
    
    /**
	 * @author Mohamed Elsamadouny
	 * 
	 * Creates a donation
	 * Checks that the visitor exits
	 *
	 * @param the visitors username
	 * @return the created donation
	 */
    
    @Transactional
    public Donation createDonation(String username, String itemName, String itemDescription) {

        // tests related to the visitor
        Visitor visitor = visitorRepository.findVisitorByUsername(username);

        // check visitor not null
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this Id was not found");
        }
        // Check if name and donation pass the requirements
        if (username.length() > 50) {
        	throw new MmssException(HttpStatus.BAD_REQUEST, "The donation name should not exceed 50 characters");
        }
        
        if (itemDescription.length() > 300) {
        	throw new MmssException(HttpStatus.BAD_REQUEST, "The donation description should not exceed 300 characters");
        }
        
        // Create the donation
        Donation donation = new Donation();
        donation.setVisitor(visitor);
        donation.setItemName(itemName);
        donation.setDescription(itemDescription);
        donation.setExchangeStatus(ExchangeStatus.Pending);
        donation.setSubmittedDate(new Date(System.currentTimeMillis()));
        
        // save the donation to the repository
        donationRepository.save(donation);
        
        // return the created donation
        return donation;
        
        
    }
    
    
    
	
	
	
	
	
	

}

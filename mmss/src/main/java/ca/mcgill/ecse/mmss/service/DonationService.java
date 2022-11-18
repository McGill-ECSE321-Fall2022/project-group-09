package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange;
import ca.mcgill.ecse.mmss.model.Room.RoomType;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.service.ArtefactService;

@Service
public class DonationService {

	// make the donation repository private
	@Autowired
	private DonationRepository donationRepository;

	@Autowired
	private VisitorRepository visitorRepository;
	
	@Autowired
    private ArtefactRepository artefactRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired 
    private NotificationService notificationService;
    
    @Autowired
    private ArtefactService artefactService;
	
	
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
			throw new MmssException(HttpStatus.NOT_FOUND, "Donation not found");
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
    public ArrayList<Donation> getAllDonationsBySubmittedDate(Date submittedDate) {

        // use the repository
        ArrayList<Donation> allDonations = donationRepository.findBySubmittedDate(submittedDate);

        return allDonations;
    }

    /**
	 * @author Mohamed Elsamadouny
	 * Finds all the donations by their status
	 *
	 * @param status to look for
	 * @return ArrayList of Donations
	 */
	
	@Transactional
    public ArrayList<Donation> getAllDonationsByStatus(ExchangeStatus status) {

        // use the repository
        ArrayList<Donation> allDonations = donationRepository.findByExchangeStatus(status);

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
    public ArrayList<Donation> getAllDonationsByVisitor(String username) {

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
    /**
	 * @author Mohamed Elsamadouny
	 * 
	 * Deletes a donation
	 * Checks that the visitor exits
	 *
	 * @param the donation id
	 */
    
    @Transactional
    public void deleteDonation(int id) {

        Donation donation = donationRepository.findDonationByExchangeId(id);
        if (donation == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The donation with this Id was not found");

        // calls the repository to delete the donation
        donationRepository.deleteById(donation.getExchangeId());
    }
    
    /**
     * @author Mohamed Elsamadouny
     * 
     * Declines a donation by deleting it from the repo and sending a notification
     * 
     * @param id
     * @param status
     * @return
     */
    
    @Transactional
    public void declineDonation(int id, ExchangeStatus status) {
    	
        Donation donation = donationRepository.findDonationByExchangeId(id);
        
        if (status == ExchangeStatus.Declined) {
        // create notification message
        String message = "Your donation request submitted on date" + donation.getSubmittedDate().toString()
        + "with name: " + String.valueOf(donation.getItemName())
        + "has been declined!";

        // send notification method
        notificationService.createNotificationByUsername(donation.getVisitor().getUsername(), message); 
    	
        // delete donation using service method
        deleteDonation(id);
        } 
    }
    
    /**
     * @author Mohamed Elsamadouny
     * 
     * Modifies the status of a Donation
     * Declined Donations are automatically deleted
     * 
     * @param id
     * @param status
     * @param canLoan
     * @param insuranceFees
     * @param loanFee
     * @return
     */
    
    @Transactional
    public Artefact updateStatus(int id, ExchangeStatus status, boolean canLoan, double insuraceFees, double loanFee) {
    	
    	// Create an Artifact Object so if the donation was approved
    	Artefact artefact = null;
 
        // get the donation if it exists
        Donation donation = donationRepository.findDonationByExchangeId(id);
        if (donation == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The donation with this Id was not found");
        } else {
            // can't set status to pending
            if (status == ExchangeStatus.Pending) {
                throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot set the status of a Donation to pending");
                // declined daontions are deleted immediately
            } else if (status == ExchangeStatus.Approved) {
            	
            	// Once Donation has been approved, create a new artifact and delete donation from the repository
                artefact = artefactService.createArtefact(donation.getItemName(), donation.getDescription(), canLoan, insuraceFees, loanFee);

                // set room to storage
                artefact.setRoom(roomRepository.findAllByRoomType(RoomType.Storage).get(0));
                
                // Persist to DB
                artefactRepository.save(artefact);
                
                // create notification message
                String message = "Your donation request submitted on date" + donation.getSubmittedDate().toString()
                        + "with name: " + String.valueOf(donation.getItemName())
                        + "has been approved! Thank you very much for your donation!";

                // send notification method
                notificationService.createNotificationByUsername(donation.getVisitor().getUsername(), message);
                
                // delete the donation from 
                deleteDonation(donation.getExchangeId());
            }
        }
        return artefact;
        
    }

}

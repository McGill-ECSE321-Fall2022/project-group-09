package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;

import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

public class DonationDto {
   
    private int exchangeId; 
    private Date submittedDate; 
    private ExchangeStatus exchangeStatus; 
    private String itemName; 
    private String description; 
    private VisitorDto visitor;

    /**
     * @author Shidan Javaheri
     * Constructor that takes donation as argument
     * @param donation
     */
    public DonationDto (Donation donation)  { 

        this.exchangeId = donation.getExchangeId();
        this.submittedDate = donation.getSubmittedDate();
        this.exchangeStatus = donation.getExchangeStatus();
        this.itemName = donation.getItemName();
        this.description = donation.getDescription();
        this.visitor = new VisitorDto (donation.getVisitor());
    }
    /**
     * @author Shidan Javaheri
     * Constructor that takes seperate arguments
     * @param exchangeId
     * @param submittedDate
     * @param exchangeStatus
     * @param itemName
     * @param description
     * @param visitor
     */
    public DonationDto(int exchangeId, Date submittedDate, ExchangeStatus exchangeStatus, String itemName,
            String description, VisitorDto visitor) {
        this.exchangeId = exchangeId;
        this.submittedDate = submittedDate;
        this.exchangeStatus = exchangeStatus;
        this.itemName = itemName;
        this.description = description;
        this.visitor = visitor;
    }
    public int getExchangeId() {
        return exchangeId;
    }
    public Date getSubmittedDate() {
        return submittedDate;
    }
    public ExchangeStatus getExchangeStatus() {
        return exchangeStatus;
    }
    public String getItemName() {
        return itemName;
    }
    public String getDescription() {
        return description;
    }
    public VisitorDto getVisitor() {
        return visitor;
    } 
    
}

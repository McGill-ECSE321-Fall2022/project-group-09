package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Artefact;

public class ArtefactDto {
    private int artefactId; 
    private String artefactName; 
    private String description; 
    private Boolean canLoan; 
    private double insuranceFee; 
    private double loanFee; 
    private boolean currentlyOnLoan; 
    private RoomDto room;
    /**
     * @author Shidan Javaheri
     * Constructor that takes in an artefact
     * @param artefact
     */
    public ArtefactDto (Artefact artefact) { 
        this.artefactId = artefact.getArtefactId(); 
        this.artefactName = artefact.getArtefactName(); 
        this.description = artefact.getDescription();
        this.canLoan = artefact.getCanLoan();
        this.insuranceFee = artefact.getInsuranceFee();
        this.loanFee = artefact.getLoanFee();
        this.currentlyOnLoan = artefact.getCurrentlyOnLoan(); 
        this.room = new RoomDto (artefact.getRoom());
    }
    /**
     * @author Shidan Javaheri
     * Constructor that takes in all arguments
     * @param artefactId
     * @param artefactName
     * @param description
     * @param canLoan
     * @param insuranceFee
     * @param loanFee
     * @param room
     */ 
    public ArtefactDto(Integer artefactId, String artefactName, String description, Boolean canLoan, double insuranceFee,double loanFee, boolean currentlyOnLoan, RoomDto room) {
        this.artefactId = artefactId;
        this.artefactName = artefactName;
        this.description = description;
        this.canLoan = canLoan;
        this.insuranceFee = insuranceFee;
        this.loanFee = loanFee;
        this.room = room;
    }
    public int getArtefactId() {
        return artefactId;
    }
    public String getArtefactName() {
        return artefactName;
    }
    public String getDescription() {
        return description;
    }
    public Boolean getCanLoan() {
        return canLoan;
    }
    public double getInsuranceFee() {
        return insuranceFee;
    }
    public double getLoanFee() {
        return loanFee;
    }
    public RoomDto getRoom() {
        return room;
    }
    public boolean isCurrentlyOnLoan() {
        return currentlyOnLoan;
    } 
    
}

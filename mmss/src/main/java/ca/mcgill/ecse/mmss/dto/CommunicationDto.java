package ca.mcgill.ecse.mmss.dto;

public class CommunicationDto {
    private int communicationId;
    /**
     * @author Shidan Javaheri
     * Constructor for communicationId
     * @param communicationId
     */
public CommunicationDto(int communicationId) {
    this.communicationId = communicationId; 
}

public int getCommunicationId() {
    return communicationId;
} 
   
   
}

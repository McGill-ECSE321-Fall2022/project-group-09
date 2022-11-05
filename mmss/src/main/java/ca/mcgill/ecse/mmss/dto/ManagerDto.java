package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Manager;

public class ManagerDto {

    private String userName; 
    private String password; 
    private PersonDto person; 
    private CommunicationDto communication;

    /**
     * @author Shidan Javaheri
     * Constructor that takes in the manager as the argument
     * @param manager
     */
    public ManagerDto (Manager manager) { 
        this.userName = manager.getUsername();
        this.password = manager.getPassword();
        this.person = new PersonDto(manager.getPerson());
        this.communication = new CommunicationDto(manager.getCommunication().getCommunicationId());
    }

    
    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguments
     * @param userName
     * @param password
     * @param person
     * @param communication
     */
    public ManagerDto(String userName, String password, PersonDto person,
            CommunicationDto communication) {
        this.userName = userName;
        this.password = password;
        this.person = person;
        this.communication = communication;
    }


    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public PersonDto getPerson() {
        return person;
    }
    public CommunicationDto getCommunication() {
        return communication;
    }


    
    
}

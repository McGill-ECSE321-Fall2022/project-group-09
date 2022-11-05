package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Person;

public class PersonDto {

    private int personId; 
    private String firstName; 
    private String lastName;
    /**
     * @author Shidan Javaheri
     * Constructor that takes a person as argument
     * @param person
     */
    public PersonDto (Person person) { 
        this.personId = person.getPersonId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }
    /**
     * @author Shidan Javaheri
     * Generic constructor with all arguments
     * @param personId
     * @param firstName
     * @param lastName
     */
    public PersonDto(int personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getPersonId() {
        return personId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    } 
    
    
}

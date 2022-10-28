/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.*;

// line 20 "../../../../../mmss.ump"
// line 205 "../../../../../mmss.ump"

@Entity
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int personId;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person () { 
    
  }
  
  public Person(int aPersonId, String aFirstName, String aLastName)
  {
    personId = aPersonId;
    firstName = aFirstName;
    lastName = aLastName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPersonId(int aPersonId)
  {
    boolean wasSet = false;
    personId = aPersonId;
    wasSet = true;
    return wasSet;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public int getPersonId()
  {
    return personId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "personId" + ":" + getPersonId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]";
  }
}
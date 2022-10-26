/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * What generalization strategies?
 */
// line 29 "../../../../../mmss.ump"
// line 136 "../../../../../mmss.ump"

@MappedSuperclass
public abstract class AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AccountType Attributes
  @Id
  private String username;
  private String password;

  //AccountType Associations
  
  @ManyToOne(optional = false)
  private Person person;
  
  @ManyToOne(optional = false)
  private Communication communication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AccountType () {} 
  
  public AccountType(String aUsername, String aPassword, Person aPerson)
  {
    username = aUsername;
    password = aPassword;
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create AccountType due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public Person getPerson()
  {
    return person;
  }
  /* Code from template association_GetOne */
  public Communication getCommunication()
  {
    return communication;
  }

  public boolean hasCommunication()
  {
    boolean has = communication != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson != null)
    {
      person = aNewPerson;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCommunication(Communication aNewCommunication)
  {
    boolean wasSet = false;
    communication = aNewCommunication;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    person = null;
    communication = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "person = "+(getPerson()!=null?Integer.toHexString(System.identityHashCode(getPerson())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "communication = "+(getCommunication()!=null?Integer.toHexString(System.identityHashCode(getCommunication())):"null");
  }
}
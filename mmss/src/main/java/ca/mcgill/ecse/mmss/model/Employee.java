/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 49 "../../../../../mmss.ump"
// line 153 "../../../../../mmss.ump"

@Entity
public class Employee extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private String phoneNumber;
  
  @Collumn (nullable = false)
  private Boolean availableForTour = true; 

  //Employee Associations
  @ManyToOne
  private Shift shift;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Employee () {}
  
  public Employee(String aUsername, String aPassword, Person aPerson, String aPhoneNumber)
  {
    super(aUsername, aPassword, aPerson);
    phoneNumber = aPhoneNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public Shift getShift()
  {
    return shift;
  }

  public boolean hasShift()
  {
    boolean has = shift != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setShift(Shift aNewShift)
  {
    boolean wasSet = false;
    shift = aNewShift;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    shift = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shift = "+(getShift()!=null?Integer.toHexString(System.identityHashCode(getShift())):"null");
  }
}
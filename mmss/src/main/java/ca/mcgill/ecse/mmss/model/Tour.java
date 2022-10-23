/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 90 "../../../../../mmss.ump"
// line 153 "../../../../../mmss.ump"
public class Tour extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tour Attributes
  private int numberOfParticipants;

  //Tour Associations
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tour(int aBookingId, Date aDate, int aPrice, int aNumberOfParticipants, Visitor aVisitor)
  {
    super(aBookingId, aDate, aPrice);
    numberOfParticipants = aNumberOfParticipants;
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Tour due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumberOfParticipants(int aNumberOfParticipants)
  {
    boolean wasSet = false;
    numberOfParticipants = aNumberOfParticipants;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfParticipants()
  {
    return numberOfParticipants;
  }
  /* Code from template association_GetOne */
  public Visitor getVisitor()
  {
    return visitor;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setVisitor(Visitor aNewVisitor)
  {
    boolean wasSet = false;
    if (aNewVisitor != null)
    {
      visitor = aNewVisitor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    visitor = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfParticipants" + ":" + getNumberOfParticipants()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 97 "../../../../../../mmss.ump"
// line 160 "../../../../../../mmss.ump"
public class Ticket extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Associations
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(int aBookingId, Date aDate, int aPricePerPerson, Visitor aVisitor)
  {
    super(aBookingId, aDate, aPricePerPerson);
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Ticket due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
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

}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 96 "../../../../../mmss.ump"
// line 179 "../../../../../mmss.ump"

@Entity
public class Ticket extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Associations
  @ManyToOne(optional = false)
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Ticket () {}
  
  public Ticket(int aBookingId, int aPricePerPerson, OpenDay aDate, Visitor aVisitor)
  {
    super(aBookingId, aPricePerPerson, aDate);
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
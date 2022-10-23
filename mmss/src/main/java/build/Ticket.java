/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 83 "mmss.ump"
// line 146 "mmss.ump"
public class Ticket extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Attributes
  private int ticketID;

  //Ticket Associations
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(Date aDate, int aPrice, int aTicketID, Visitor aVisitor)
  {
    super(aDate, aPrice);
    ticketID = aTicketID;
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Ticket due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTicketID(int aTicketID)
  {
    boolean wasSet = false;
    ticketID = aTicketID;
    wasSet = true;
    return wasSet;
  }

  public int getTicketID()
  {
    return ticketID;
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
            "ticketID" + ":" + getTicketID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
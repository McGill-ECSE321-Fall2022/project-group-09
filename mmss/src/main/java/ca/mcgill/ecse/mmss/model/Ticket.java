/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 98 "../../../../../mmss.ump"
// line 164 "../../../../../mmss.ump"
public class Ticket extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Attributes
  private String ticketID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(Date aDate, int aPrice, String aTicketID)
  {
    super(aDate, aPrice);
    ticketID = aTicketID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTicketID(String aTicketID)
  {
    boolean wasSet = false;
    ticketID = aTicketID;
    wasSet = true;
    return wasSet;
  }

  public String getTicketID()
  {
    return ticketID;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "ticketID" + ":" + getTicketID()+ "]";
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 89 "../../../../../../mmss.ump"
// line 155 "../../../../../../mmss.ump"
public abstract class Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  private int bookingId;
  private Date date;
  private int pricePerPerson;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking(int aBookingId, Date aDate, int aPricePerPerson)
  {
    bookingId = aBookingId;
    date = aDate;
    pricePerPerson = aPricePerPerson;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBookingId(int aBookingId)
  {
    boolean wasSet = false;
    bookingId = aBookingId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPricePerPerson(int aPricePerPerson)
  {
    boolean wasSet = false;
    pricePerPerson = aPricePerPerson;
    wasSet = true;
    return wasSet;
  }

  public int getBookingId()
  {
    return bookingId;
  }

  public Date getDate()
  {
    return date;
  }

  public int getPricePerPerson()
  {
    return pricePerPerson;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "bookingId" + ":" + getBookingId()+ "," +
            "pricePerPerson" + ":" + getPricePerPerson()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 78 "../../../../../mmss.ump"
// line 143 "../../../../../mmss.ump"
public abstract class Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  private int bookingId;
  private Date date;
  private int price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking(int aBookingId, Date aDate, int aPrice)
  {
    bookingId = aBookingId;
    date = aDate;
    price = aPrice;
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

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
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

  public int getPrice()
  {
    return price;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "bookingId" + ":" + getBookingId()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.*;

// line 88 "../../../../../mmss.ump"
// line 172 "../../../../../mmss.ump"
@MappedSuperclass
public abstract class Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int bookingId;

  @Column(nullable = false)
  private int pricePerPerson;

  //Booking Associations
  @ManyToOne(optional = false)
  private OpenDay date;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking () {}
  
  
  public Booking(int aBookingId, int aPricePerPerson, OpenDay aDate)
  {
    bookingId = aBookingId;
    pricePerPerson = aPricePerPerson;
    if (!setDate(aDate))
    {
      throw new RuntimeException("Unable to create Booking due to aDate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public int getPricePerPerson()
  {
    return pricePerPerson;
  }
  /* Code from template association_GetOne */
  public OpenDay getDate()
  {
    return date;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDate(OpenDay aNewDate)
  {
    boolean wasSet = false;
    if (aNewDate != null)
    {
      date = aNewDate;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    date = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "bookingId" + ":" + getBookingId()+ "," +
            "pricePerPerson" + ":" + getPricePerPerson()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date = "+(getDate()!=null?Integer.toHexString(System.identityHashCode(getDate())):"null");
  }
}
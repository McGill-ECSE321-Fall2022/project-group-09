/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 104 "../../../../../../model.ump"
// line 190 "../../../../../../model.ump"

@Entity
public class Tour extends Booking
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ShiftTime { Morning, Afternoon, Evening }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tour Attributes
  @Column(nullable = false)
  private int numberOfParticipants;
  @Column(nullable = false)
  private ShiftTime tourTime;

  //Tour Associations
  @ManyToOne
  private Employee tourGuide;
  
  @ManyToOne(optional = false)
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Tour () {}

  public Tour(int aBookingId, int aPricePerPerson, OpenDay aDate, int aNumberOfParticipants, ShiftTime aTourTime, Visitor aVisitor)
  {
    super(aBookingId, aPricePerPerson, aDate);
    numberOfParticipants = aNumberOfParticipants;
    tourTime = aTourTime;
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

  public boolean setTourTime(ShiftTime aTourTime)
  {
    boolean wasSet = false;
    tourTime = aTourTime;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfParticipants()
  {
    return numberOfParticipants;
  }

  public ShiftTime getTourTime()
  {
    return tourTime;
  }
  /* Code from template association_GetOne */
  public Employee getTourGuide()
  {
    return tourGuide;
  }

  public boolean hasTourGuide()
  {
    boolean has = tourGuide != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Visitor getVisitor()
  {
    return visitor;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setTourGuide(Employee aNewTourGuide)
  {
    boolean wasSet = false;
    tourGuide = aNewTourGuide;
    wasSet = true;
    return wasSet;
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
    tourGuide = null;
    visitor = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfParticipants" + ":" + getNumberOfParticipants()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tourTime" + "=" + (getTourTime() != null ? !getTourTime().equals(this)  ? getTourTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tourGuide = "+(getTourGuide()!=null?Integer.toHexString(System.identityHashCode(getTourGuide())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
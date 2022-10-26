/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 101 "../../../../../../model.ump"
// line 186 "../../../../../../model.ump"

@Entity
public class Tour extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tour Attributes
  private int numberOfParticipants;

  //Tour State Machines
  public enum Enum { Morning, Afternoon, Evening }
  private Enum enum;

  //Tour Associations
  
  @ManyToOne(optional = false)
  private Employee tourGuide;
  
  @ManyToOne(optional = false)
  @Column(nullable=false)
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Tour () {}
  
  public Tour(int aBookingId, int aPricePerPerson, OpenDay aDate, int aNumberOfParticipants, Visitor aVisitor)
  {
    super(aBookingId, aPricePerPerson, aDate);
    numberOfParticipants = aNumberOfParticipants;
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Tour due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setEnum(Enum.Morning);
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

  public String getEnumFullName()
  {
    String answer = enum.toString();
    return answer;
  }

  public Enum getEnum()
  {
    return enum;
  }

  public boolean setEnum(Enum aEnum)
  {
    enum = aEnum;
    return true;
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
            "  " + "tourGuide = "+(getTourGuide()!=null?Integer.toHexString(System.identityHashCode(getTourGuide())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
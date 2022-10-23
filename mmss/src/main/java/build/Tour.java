/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 88 "mmss.ump"
// line 151 "mmss.ump"
public class Tour extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tour Attributes
  private int tourID;
  private int numberOfParticipants;

  //Tour Associations
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tour(Date aDate, int aPrice, int aTourID, int aNumberOfParticipants, Visitor aVisitor)
  {
    super(aDate, aPrice);
    tourID = aTourID;
    numberOfParticipants = aNumberOfParticipants;
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Tour due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTourID(int aTourID)
  {
    boolean wasSet = false;
    tourID = aTourID;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfParticipants(int aNumberOfParticipants)
  {
    boolean wasSet = false;
    numberOfParticipants = aNumberOfParticipants;
    wasSet = true;
    return wasSet;
  }

  public int getTourID()
  {
    return tourID;
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
            "tourID" + ":" + getTourID()+ "," +
            "numberOfParticipants" + ":" + getNumberOfParticipants()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
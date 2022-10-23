/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 102 "../../../../../../mmss.ump"
// line 167 "../../../../../../mmss.ump"
public class Tour extends Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tour Attributes
  private String tourID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tour(Date aDate, int aPrice, String aTourID)
  {
    super(aDate, aPrice);
    tourID = aTourID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTourID(String aTourID)
  {
    boolean wasSet = false;
    tourID = aTourID;
    wasSet = true;
    return wasSet;
  }

  public String getTourID()
  {
    return tourID;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "tourID" + ":" + getTourID()+ "]";
  }
}
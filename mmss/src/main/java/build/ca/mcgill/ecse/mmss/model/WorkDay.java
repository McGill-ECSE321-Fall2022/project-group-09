/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 84 "../../../../../../mmss.ump"
// line 152 "../../../../../../mmss.ump"
public class WorkDay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkDay Attributes
  private Date workingDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkDay(Date aWorkingDate)
  {
    workingDate = aWorkingDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWorkingDate(Date aWorkingDate)
  {
    boolean wasSet = false;
    workingDate = aWorkingDate;
    wasSet = true;
    return wasSet;
  }

  public Date getWorkingDate()
  {
    return workingDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "workingDate" + "=" + (getWorkingDate() != null ? !getWorkingDate().equals(this)  ? getWorkingDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 78 "../../../../../../mmss.ump"
// line 227 "../../../../../../mmss.ump"
public class OpenDay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OpenDay Attributes
  private int openDayId;
  private Date date;

  //OpenDay Associations
  private WeeklySchedule weeklySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OpenDay(int aOpenDayId, Date aDate, WeeklySchedule aWeeklySchedule)
  {
    openDayId = aOpenDayId;
    date = aDate;
    if (!setWeeklySchedule(aWeeklySchedule))
    {
      throw new RuntimeException("Unable to create OpenDay due to aWeeklySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpenDayId(int aOpenDayId)
  {
    boolean wasSet = false;
    openDayId = aOpenDayId;
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

  public int getOpenDayId()
  {
    return openDayId;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public WeeklySchedule getWeeklySchedule()
  {
    return weeklySchedule;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setWeeklySchedule(WeeklySchedule aNewWeeklySchedule)
  {
    boolean wasSet = false;
    if (aNewWeeklySchedule != null)
    {
      weeklySchedule = aNewWeeklySchedule;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    weeklySchedule = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "openDayId" + ":" + getOpenDayId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weeklySchedule = "+(getWeeklySchedule()!=null?Integer.toHexString(System.identityHashCode(getWeeklySchedule())):"null");
  }
}
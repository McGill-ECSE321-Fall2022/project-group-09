/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 75 "../../../../../../model.ump"
// line 231 "../../../../../../model.ump"
// line 243 "../../../../../../model.ump"

@Entity
public class Shift
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ShiftTime { Morning, Afternoon, Evening }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)  
  private int shiftId;
  
  private ShiftTime shiftTime;

  //Shift Associations
  @ManyToOne(optional = false)
  private WeeklySchedule weeklySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Shift() {}

  public Shift(int aShiftId, ShiftTime aShiftTime, WeeklySchedule aWeeklySchedule)
  {
    shiftId = aShiftId;
    shiftTime = aShiftTime;
    if (!setWeeklySchedule(aWeeklySchedule))
    {
      throw new RuntimeException("Unable to create Shift due to aWeeklySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setShiftId(int aShiftId)
  {
    boolean wasSet = false;
    shiftId = aShiftId;
    wasSet = true;
    return wasSet;
  }

  public boolean setShiftTime(ShiftTime aShiftTime)
  {
    boolean wasSet = false;
    shiftTime = aShiftTime;
    wasSet = true;
    return wasSet;
  }

  public int getShiftId()
  {
    return shiftId;
  }

  public ShiftTime getShiftTime()
  {
    return shiftTime;
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
            "shiftId" + ":" + getShiftId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shiftTime" + "=" + (getShiftTime() != null ? !getShiftTime().equals(this)  ? getShiftTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weeklySchedule = "+(getWeeklySchedule()!=null?Integer.toHexString(System.identityHashCode(getWeeklySchedule())):"null");
  }
}
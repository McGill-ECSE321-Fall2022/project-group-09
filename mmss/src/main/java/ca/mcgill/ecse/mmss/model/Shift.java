/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.*;

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

  @Column(nullable = false)
  private ShiftTime shiftTime;

  //Shift Associations
  @ManyToOne(optional = false)
  private Schedule Schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Shift() {}

  public Shift(int aShiftId, ShiftTime aShiftTime, Schedule aSchedule)
  {
    shiftId = aShiftId;
    shiftTime = aShiftTime;
    if (!setSchedule(aSchedule))
    {
      throw new RuntimeException("Unable to create Shift due to aSchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Schedule getSchedule()
  {
    return Schedule;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSchedule(Schedule aNewSchedule)
  {
    boolean wasSet = false;
    if (aNewSchedule != null)
    {
      Schedule = aNewSchedule;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    Schedule = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "shiftId" + ":" + getShiftId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shiftTime" + "=" + (getShiftTime() != null ? !getShiftTime().equals(this)  ? getShiftTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "Schedule = "+(getSchedule()!=null?Integer.toHexString(System.identityHashCode(getSchedule())):"null");
  }
}
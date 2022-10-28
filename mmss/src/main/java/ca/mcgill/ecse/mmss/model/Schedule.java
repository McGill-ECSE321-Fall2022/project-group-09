/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 82 "../../../../../mmss.ump"
// line 252 "../../../../../mmss.ump"

@Entity
public class Schedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Schedule Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int scheduleId;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Schedule () {}

  public Schedule(int aScheduleId)
  {
    scheduleId = aScheduleId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScheduleId(int aScheduleId)
  {
    boolean wasSet = false;
    scheduleId = aScheduleId;
    wasSet = true;
    return wasSet;
  }

  public int getScheduleId()
  {
    return scheduleId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "ScheduleId" + ":" + getScheduleId()+ "]";
  }
}
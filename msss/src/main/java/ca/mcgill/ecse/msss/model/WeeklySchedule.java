/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 82 "../../../../../mmss.ump"
// line 252 "../../../../../mmss.ump"

@Entity
public class WeeklySchedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeeklySchedule Attributes
  @Id
  @GeneratedValue(Strategy = GenerationType.AUTO)
  private int weeklyScheduleId;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected WeeklySchedule () {}

  public WeeklySchedule(int aWeeklyScheduleId)
  {
    weeklyScheduleId = aWeeklyScheduleId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeeklyScheduleId(int aWeeklyScheduleId)
  {
    boolean wasSet = false;
    weeklyScheduleId = aWeeklyScheduleId;
    wasSet = true;
    return wasSet;
  }

  public int getWeeklyScheduleId()
  {
    return weeklyScheduleId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "weeklyScheduleId" + ":" + getWeeklyScheduleId()+ "]";
  }
}
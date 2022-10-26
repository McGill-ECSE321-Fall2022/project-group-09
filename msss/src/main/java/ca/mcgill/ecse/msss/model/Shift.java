/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 72 "../../../../../mmss.ump"
// line 225 "../../../../../mmss.ump"
// line 237 "../../../../../mmss.ump"

@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  @Id
  @GeneratedValue(Strategy = GenerationType.AUTO)
  private int shiftId;

  //Shift State Machines
  public enum Enum { Morning, Afternoon, Evening }
  private Enum enum;

  //Shift Associations
  
  @ManyToOne(optional = false)
  @Column(nullable=false)
  private WeeklySchedule weeklySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected Shift () {}

  public Shift(int aShiftId, WeeklySchedule aWeeklySchedule)
  {
    shiftId = aShiftId;
    if (!setWeeklySchedule(aWeeklySchedule))
    {
      throw new RuntimeException("Unable to create Shift due to aWeeklySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setEnum(Enum.Morning);
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

  public int getShiftId()
  {
    return shiftId;
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
            "  " + "weeklySchedule = "+(getWeeklySchedule()!=null?Integer.toHexString(System.identityHashCode(getWeeklySchedule())):"null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 78 "../../../../../mmss.ump"
// line 247 "../../../../../mmss.ump"

@Entity
public class OpenDay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OpenDay Attributes
  
  @Id
  private Date date;

  //OpenDay Associations
  
  @ManyToOne(optional = false)
  private WeeklySchedule weeklySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public OpenDay () {}

  public OpenDay(Date aDate)
  {
    date = aDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
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

  public boolean hasWeeklySchedule()
  {
    boolean has = weeklySchedule != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWeeklySchedule(WeeklySchedule aNewWeeklySchedule)
  {
    boolean wasSet = false;
    weeklySchedule = aNewWeeklySchedule;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    weeklySchedule = null;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weeklySchedule = "+(getWeeklySchedule()!=null?Integer.toHexString(System.identityHashCode(getWeeklySchedule())):"null");
  }
}
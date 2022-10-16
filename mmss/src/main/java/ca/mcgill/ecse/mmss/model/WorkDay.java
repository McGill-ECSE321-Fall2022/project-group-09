/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import java.util.*;

// line 98 "../../../../../mmss.ump"
// line 205 "../../../../../mmss.ump"
public class WorkDay
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Date, WorkDay> workdaysByWorkingDate = new HashMap<Date, WorkDay>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkDay Attributes
  private Date workingDate;

  //WorkDay Associations
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkDay(Date aWorkingDate, MuseumManagement aMuseumManagement)
  {
    if (!setWorkingDate(aWorkingDate))
    {
      throw new RuntimeException("Cannot create due to duplicate workingDate. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create workingDay due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWorkingDate(Date aWorkingDate)
  {
    boolean wasSet = false;
    Date anOldWorkingDate = getWorkingDate();
    if (anOldWorkingDate != null && anOldWorkingDate.equals(aWorkingDate)) {
      return true;
    }
    if (hasWithWorkingDate(aWorkingDate)) {
      return wasSet;
    }
    workingDate = aWorkingDate;
    wasSet = true;
    if (anOldWorkingDate != null) {
      workdaysByWorkingDate.remove(anOldWorkingDate);
    }
    workdaysByWorkingDate.put(aWorkingDate, this);
    return wasSet;
  }

  public Date getWorkingDate()
  {
    return workingDate;
  }
  /* Code from template attribute_GetUnique */
  public static WorkDay getWithWorkingDate(Date aWorkingDate)
  {
    return workdaysByWorkingDate.get(aWorkingDate);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithWorkingDate(Date aWorkingDate)
  {
    return getWithWorkingDate(aWorkingDate) != null;
  }
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseumManagement(MuseumManagement aMuseumManagement)
  {
    boolean wasSet = false;
    if (aMuseumManagement == null)
    {
      return wasSet;
    }

    MuseumManagement existingMuseumManagement = museumManagement;
    museumManagement = aMuseumManagement;
    if (existingMuseumManagement != null && !existingMuseumManagement.equals(aMuseumManagement))
    {
      existingMuseumManagement.removeWorkingDay(this);
    }
    museumManagement.addWorkingDay(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    workdaysByWorkingDate.remove(getWorkingDate());
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeWorkingDay(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "workingDate" + "=" + (getWorkingDate() != null ? !getWorkingDate().equals(this)  ? getWorkingDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}
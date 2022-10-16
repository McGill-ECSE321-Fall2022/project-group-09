/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 71 "../../../../../mmss.ump"
// line 189 "../../../../../mmss.ump"
public class Employee extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<WorkDay> atWorkDays;
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aUsername, String aPassword, MuseumManagement aMuseumManagement)
  {
    super(aUsername, aPassword);
    atWorkDays = new ArrayList<WorkDay>();
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create employee due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public WorkDay getAtWorkDay(int index)
  {
    WorkDay aAtWorkDay = atWorkDays.get(index);
    return aAtWorkDay;
  }

  public List<WorkDay> getAtWorkDays()
  {
    List<WorkDay> newAtWorkDays = Collections.unmodifiableList(atWorkDays);
    return newAtWorkDays;
  }

  public int numberOfAtWorkDays()
  {
    int number = atWorkDays.size();
    return number;
  }

  public boolean hasAtWorkDays()
  {
    boolean has = atWorkDays.size() > 0;
    return has;
  }

  public int indexOfAtWorkDay(WorkDay aAtWorkDay)
  {
    int index = atWorkDays.indexOf(aAtWorkDay);
    return index;
  }
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAtWorkDays()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addAtWorkDay(WorkDay aAtWorkDay)
  {
    boolean wasAdded = false;
    if (atWorkDays.contains(aAtWorkDay)) { return false; }
    atWorkDays.add(aAtWorkDay);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAtWorkDay(WorkDay aAtWorkDay)
  {
    boolean wasRemoved = false;
    if (atWorkDays.contains(aAtWorkDay))
    {
      atWorkDays.remove(aAtWorkDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAtWorkDayAt(WorkDay aAtWorkDay, int index)
  {  
    boolean wasAdded = false;
    if(addAtWorkDay(aAtWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAtWorkDays()) { index = numberOfAtWorkDays() - 1; }
      atWorkDays.remove(aAtWorkDay);
      atWorkDays.add(index, aAtWorkDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAtWorkDayAt(WorkDay aAtWorkDay, int index)
  {
    boolean wasAdded = false;
    if(atWorkDays.contains(aAtWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAtWorkDays()) { index = numberOfAtWorkDays() - 1; }
      atWorkDays.remove(aAtWorkDay);
      atWorkDays.add(index, aAtWorkDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAtWorkDayAt(aAtWorkDay, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setMuseumManagement(MuseumManagement aMuseumManagement)
  {
    boolean wasSet = false;
    //Must provide museumManagement to employee
    if (aMuseumManagement == null)
    {
      return wasSet;
    }

    //museumManagement already at maximum (20)
    if (aMuseumManagement.numberOfEmployees() >= MuseumManagement.maximumNumberOfEmployees())
    {
      return wasSet;
    }
    
    MuseumManagement existingMuseumManagement = museumManagement;
    museumManagement = aMuseumManagement;
    if (existingMuseumManagement != null && !existingMuseumManagement.equals(aMuseumManagement))
    {
      boolean didRemove = existingMuseumManagement.removeEmployee(this);
      if (!didRemove)
      {
        museumManagement = existingMuseumManagement;
        return wasSet;
      }
    }
    museumManagement.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    atWorkDays.clear();
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeEmployee(this);
    }
    super.delete();
  }

}
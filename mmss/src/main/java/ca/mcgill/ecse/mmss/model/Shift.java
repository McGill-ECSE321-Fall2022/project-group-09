/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 79 "../../../../../mmss.ump"
// line 203 "../../../../../mmss.ump"
// line 215 "../../../../../mmss.ump"
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift State Machines
  public enum Enum { Morning, Afternoon, Evening }
  private Enum enum;

  //Shift Associations
  private List<Employee> workingOnShift;
  private List<WorkDay> workDaysOnShift;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift()
  {
    workingOnShift = new ArrayList<Employee>();
    workDaysOnShift = new ArrayList<WorkDay>();
    setEnum(Enum.Morning);
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetMany */
  public Employee getWorkingOnShift(int index)
  {
    Employee aWorkingOnShift = workingOnShift.get(index);
    return aWorkingOnShift;
  }

  public List<Employee> getWorkingOnShift()
  {
    List<Employee> newWorkingOnShift = Collections.unmodifiableList(workingOnShift);
    return newWorkingOnShift;
  }

  public int numberOfWorkingOnShift()
  {
    int number = workingOnShift.size();
    return number;
  }

  public boolean hasWorkingOnShift()
  {
    boolean has = workingOnShift.size() > 0;
    return has;
  }

  public int indexOfWorkingOnShift(Employee aWorkingOnShift)
  {
    int index = workingOnShift.indexOf(aWorkingOnShift);
    return index;
  }
  /* Code from template association_GetMany */
  public WorkDay getWorkDaysOnShift(int index)
  {
    WorkDay aWorkDaysOnShift = workDaysOnShift.get(index);
    return aWorkDaysOnShift;
  }

  public List<WorkDay> getWorkDaysOnShift()
  {
    List<WorkDay> newWorkDaysOnShift = Collections.unmodifiableList(workDaysOnShift);
    return newWorkDaysOnShift;
  }

  public int numberOfWorkDaysOnShift()
  {
    int number = workDaysOnShift.size();
    return number;
  }

  public boolean hasWorkDaysOnShift()
  {
    boolean has = workDaysOnShift.size() > 0;
    return has;
  }

  public int indexOfWorkDaysOnShift(WorkDay aWorkDaysOnShift)
  {
    int index = workDaysOnShift.indexOf(aWorkDaysOnShift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkingOnShift()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWorkingOnShift(Employee aWorkingOnShift)
  {
    boolean wasAdded = false;
    if (workingOnShift.contains(aWorkingOnShift)) { return false; }
    workingOnShift.add(aWorkingOnShift);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkingOnShift(Employee aWorkingOnShift)
  {
    boolean wasRemoved = false;
    if (workingOnShift.contains(aWorkingOnShift))
    {
      workingOnShift.remove(aWorkingOnShift);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkingOnShiftAt(Employee aWorkingOnShift, int index)
  {  
    boolean wasAdded = false;
    if(addWorkingOnShift(aWorkingOnShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkingOnShift()) { index = numberOfWorkingOnShift() - 1; }
      workingOnShift.remove(aWorkingOnShift);
      workingOnShift.add(index, aWorkingOnShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkingOnShiftAt(Employee aWorkingOnShift, int index)
  {
    boolean wasAdded = false;
    if(workingOnShift.contains(aWorkingOnShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkingOnShift()) { index = numberOfWorkingOnShift() - 1; }
      workingOnShift.remove(aWorkingOnShift);
      workingOnShift.add(index, aWorkingOnShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkingOnShiftAt(aWorkingOnShift, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkDaysOnShift()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWorkDaysOnShift(WorkDay aWorkDaysOnShift)
  {
    boolean wasAdded = false;
    if (workDaysOnShift.contains(aWorkDaysOnShift)) { return false; }
    workDaysOnShift.add(aWorkDaysOnShift);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkDaysOnShift(WorkDay aWorkDaysOnShift)
  {
    boolean wasRemoved = false;
    if (workDaysOnShift.contains(aWorkDaysOnShift))
    {
      workDaysOnShift.remove(aWorkDaysOnShift);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkDaysOnShiftAt(WorkDay aWorkDaysOnShift, int index)
  {  
    boolean wasAdded = false;
    if(addWorkDaysOnShift(aWorkDaysOnShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaysOnShift()) { index = numberOfWorkDaysOnShift() - 1; }
      workDaysOnShift.remove(aWorkDaysOnShift);
      workDaysOnShift.add(index, aWorkDaysOnShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkDaysOnShiftAt(WorkDay aWorkDaysOnShift, int index)
  {
    boolean wasAdded = false;
    if(workDaysOnShift.contains(aWorkDaysOnShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaysOnShift()) { index = numberOfWorkDaysOnShift() - 1; }
      workDaysOnShift.remove(aWorkDaysOnShift);
      workDaysOnShift.add(index, aWorkDaysOnShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkDaysOnShiftAt(aWorkDaysOnShift, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    workingOnShift.clear();
    workDaysOnShift.clear();
  }

}
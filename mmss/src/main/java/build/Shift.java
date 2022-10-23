/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 70 "mmss.ump"
// line 187 "mmss.ump"
// line 199 "mmss.ump"
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private int shiftId;
  private Date workingDate;

  //Shift State Machines
  public enum Enum { Morning, Afternoon, Evening }
  private Enum enum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(int aShiftId, Date aWorkingDate)
  {
    shiftId = aShiftId;
    workingDate = aWorkingDate;
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

  public boolean setWorkingDate(Date aWorkingDate)
  {
    boolean wasSet = false;
    workingDate = aWorkingDate;
    wasSet = true;
    return wasSet;
  }

  public int getShiftId()
  {
    return shiftId;
  }

  public Date getWorkingDate()
  {
    return workingDate;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "shiftId" + ":" + getShiftId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "workingDate" + "=" + (getWorkingDate() != null ? !getWorkingDate().equals(this)  ? getWorkingDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
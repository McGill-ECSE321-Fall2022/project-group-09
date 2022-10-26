/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 54 "../../../../../mmss.ump"
// line 200 "../../../../../mmss.ump"

@MappedSuperClass
public abstract class Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exchange Attributes  
  @Id
  @GeneratedValue(Strategy = GenerationType.AUTO)
  private int exchangeId;
  private Date submittedDate;

  //Exchange State Machines
  public enum Enum { Pending, Declined, Approved }
  private Enum enum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Exchange () {}
  
  public Exchange(int aExchangeId, Date aSubmittedDate)
  {
    exchangeId = aExchangeId;
    submittedDate = aSubmittedDate;
    setEnum(Enum.Pending);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setExchangeId(int aExchangeId)
  {
    boolean wasSet = false;
    exchangeId = aExchangeId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSubmittedDate(Date aSubmittedDate)
  {
    boolean wasSet = false;
    submittedDate = aSubmittedDate;
    wasSet = true;
    return wasSet;
  }

  public int getExchangeId()
  {
    return exchangeId;
  }

  public Date getSubmittedDate()
  {
    return submittedDate;
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
            "exchangeId" + ":" + getExchangeId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedDate" + "=" + (getSubmittedDate() != null ? !getSubmittedDate().equals(this)  ? getSubmittedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 53 "../../../../../mmss.ump"
// line 166 "../../../../../mmss.ump"
public abstract class Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exchange Attributes
  private int exchangeId;
  private Date submittedDate;
  private boolean approved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Exchange(int aExchangeId, Date aSubmittedDate)
  {
    exchangeId = aExchangeId;
    submittedDate = aSubmittedDate;
    approved = false;
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

  public boolean setApproved(boolean aApproved)
  {
    boolean wasSet = false;
    approved = aApproved;
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

  public boolean getApproved()
  {
    return approved;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isApproved()
  {
    return approved;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "exchangeId" + ":" + getExchangeId()+ "," +
            "approved" + ":" + getApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedDate" + "=" + (getSubmittedDate() != null ? !getSubmittedDate().equals(this)  ? getSubmittedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
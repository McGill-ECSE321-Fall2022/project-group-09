/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 51 "mmss.ump"
// line 164 "mmss.ump"
public abstract class Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exchange Attributes
  private Date submittedDate;
  private boolean approved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Exchange(Date aSubmittedDate)
  {
    submittedDate = aSubmittedDate;
    approved = false;
  }

  //------------------------
  // INTERFACE
  //------------------------

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
            "approved" + ":" + getApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedDate" + "=" + (getSubmittedDate() != null ? !getSubmittedDate().equals(this)  ? getSubmittedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
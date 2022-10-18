/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 65 "../../../../../mmss.ump"
// line 144 "../../../../../mmss.ump"
public class Loan extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private String loanId;
  private Date dueDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Date aSubmittedDate, boolean aApproved, String aLoanId, Date aDueDate)
  {
    super(aSubmittedDate, aApproved);
    loanId = aLoanId;
    dueDate = aDueDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLoanId(String aLoanId)
  {
    boolean wasSet = false;
    loanId = aLoanId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDueDate(Date aDueDate)
  {
    boolean wasSet = false;
    dueDate = aDueDate;
    wasSet = true;
    return wasSet;
  }

  public String getLoanId()
  {
    return loanId;
  }

  public Date getDueDate()
  {
    return dueDate;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "loanId" + ":" + getLoanId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dueDate" + "=" + (getDueDate() != null ? !getDueDate().equals(this)  ? getDueDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
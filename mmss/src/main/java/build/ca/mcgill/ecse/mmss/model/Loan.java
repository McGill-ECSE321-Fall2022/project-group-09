/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 63 "../../../../../../mmss.ump"
// line 142 "../../../../../../mmss.ump"
public class Loan extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private String loanId;
  private Date dueDate;

  //Loan Associations
  private Artefact artefact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Date aSubmittedDate, boolean aApproved, String aLoanId, Date aDueDate, Artefact aArtefact)
  {
    super(aSubmittedDate, aApproved);
    loanId = aLoanId;
    dueDate = aDueDate;
    if (!setArtefact(aArtefact))
    {
      throw new RuntimeException("Unable to create Loan due to aArtefact. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public Artefact getArtefact()
  {
    return artefact;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setArtefact(Artefact aNewArtefact)
  {
    boolean wasSet = false;
    if (aNewArtefact != null)
    {
      artefact = aNewArtefact;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    artefact = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "loanId" + ":" + getLoanId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dueDate" + "=" + (getDueDate() != null ? !getDueDate().equals(this)  ? getDueDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artefact = "+(getArtefact()!=null?Integer.toHexString(System.identityHashCode(getArtefact())):"null");
  }
}
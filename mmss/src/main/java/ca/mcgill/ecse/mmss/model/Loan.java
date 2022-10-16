/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 84 "../../../../../mmss.ump"
// line 195 "../../../../../mmss.ump"
public class Loan extends Exchange
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextLoanId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private Date dueDate;

  //Autounique Attributes
  private int loanId;

  //Loan Associations
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Date aSubmittedDate, boolean aApproved, Date aDueDate, MuseumManagement aMuseumManagement)
  {
    super(aSubmittedDate, aApproved);
    dueDate = aDueDate;
    loanId = nextLoanId++;
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create loan due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDueDate(Date aDueDate)
  {
    boolean wasSet = false;
    dueDate = aDueDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDueDate()
  {
    return dueDate;
  }

  public int getLoanId()
  {
    return loanId;
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
      existingMuseumManagement.removeLoan(this);
    }
    museumManagement.addLoan(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeLoan(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "loanId" + ":" + getLoanId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dueDate" + "=" + (getDueDate() != null ? !getDueDate().equals(this)  ? getDueDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}
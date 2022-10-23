/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 57 "mmss.ump"
// line 131 "mmss.ump"
public class Loan extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private int loanId;
  private Date dueDate;

  //Loan Associations
  private Artefact artefact;
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Date aSubmittedDate, int aLoanId, Date aDueDate, Artefact aArtefact, Visitor aVisitor)
  {
    super(aSubmittedDate);
    loanId = aLoanId;
    dueDate = aDueDate;
    if (!setArtefact(aArtefact))
    {
      throw new RuntimeException("Unable to create Loan due to aArtefact. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Loan due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLoanId(int aLoanId)
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

  public int getLoanId()
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
  /* Code from template association_GetOne */
  public Visitor getVisitor()
  {
    return visitor;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setVisitor(Visitor aNewVisitor)
  {
    boolean wasSet = false;
    if (aNewVisitor != null)
    {
      visitor = aNewVisitor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    artefact = null;
    visitor = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "loanId" + ":" + getLoanId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dueDate" + "=" + (getDueDate() != null ? !getDueDate().equals(this)  ? getDueDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artefact = "+(getArtefact()!=null?Integer.toHexString(System.identityHashCode(getArtefact())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
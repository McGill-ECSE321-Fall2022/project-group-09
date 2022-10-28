/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

// line 61 "../../../../../mmss.ump"
// line 159 "../../../../../mmss.ump"

@Entity
public class Loan extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Associations
  @ManyToOne
  private OpenDay dueDate;
  
  @OneToOne(optional = false)
  private Artefact artefact;
  
  @ManyToOne(optional = false)
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Loan () {}

  public Loan(int aExchangeId, Date aSubmittedDate, Artefact aArtefact, Visitor aVisitor)
  {
    super(aExchangeId, aSubmittedDate);
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
  /* Code from template association_GetOne */
  public OpenDay getDueDate()
  {
    return dueDate;
  }

  public boolean hasDueDate()
  {
    boolean has = dueDate != null;
    return has;
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
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setDueDate(OpenDay aNewDueDate)
  {
    boolean wasSet = false;
    dueDate = aNewDueDate;
    wasSet = true;
    return wasSet;
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
    dueDate = null;
    artefact = null;
    visitor = null;
    super.delete();
  }

}
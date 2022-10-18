/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 3 "../../../../../mmss.ump"
// line 193 "../../../../../mmss.ump"
// line 198 "../../../../../mmss.ump"
// line 210 "../../../../../mmss.ump"
public class Artefact
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artefact Attributes
  private String artefactId;
  private String artefactName;
  private String description;
  private boolean canLoan;
  private int insuranceFee;
  private int loanFee;

  //Artefact Associations
  private Loan loan;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artefact(String aArtefactId, String aArtefactName, String aDescription, boolean aCanLoan, int aInsuranceFee, int aLoanFee)
  {
    artefactId = aArtefactId;
    artefactName = aArtefactName;
    description = aDescription;
    canLoan = aCanLoan;
    insuranceFee = aInsuranceFee;
    loanFee = aLoanFee;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setArtefactId(String aArtefactId)
  {
    boolean wasSet = false;
    artefactId = aArtefactId;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtefactName(String aArtefactName)
  {
    boolean wasSet = false;
    artefactName = aArtefactName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCanLoan(boolean aCanLoan)
  {
    boolean wasSet = false;
    canLoan = aCanLoan;
    wasSet = true;
    return wasSet;
  }

  public boolean setInsuranceFee(int aInsuranceFee)
  {
    boolean wasSet = false;
    insuranceFee = aInsuranceFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanFee(int aLoanFee)
  {
    boolean wasSet = false;
    loanFee = aLoanFee;
    wasSet = true;
    return wasSet;
  }

  public String getArtefactId()
  {
    return artefactId;
  }

  public String getArtefactName()
  {
    return artefactName;
  }

  public String getDescription()
  {
    return description;
  }

  public boolean getCanLoan()
  {
    return canLoan;
  }

  public int getInsuranceFee()
  {
    return insuranceFee;
  }

  public int getLoanFee()
  {
    return loanFee;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isCanLoan()
  {
    return canLoan;
  }
  /* Code from template association_GetOne */
  public Loan getLoan()
  {
    return loan;
  }

  public boolean hasLoan()
  {
    boolean has = loan != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setLoan(Loan aNewLoan)
  {
    boolean wasSet = false;
    loan = aNewLoan;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    loan = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "artefactId" + ":" + getArtefactId()+ "," +
            "artefactName" + ":" + getArtefactName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "canLoan" + ":" + getCanLoan()+ "," +
            "insuranceFee" + ":" + getInsuranceFee()+ "," +
            "loanFee" + ":" + getLoanFee()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "loan = "+(getLoan()!=null?Integer.toHexString(System.identityHashCode(getLoan())):"null");
  }
}
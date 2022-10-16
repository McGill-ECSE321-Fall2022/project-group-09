/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 20 "../../../../../mmss.ump"
// line 128 "../../../../../mmss.ump"
// line 163 "../../../../../mmss.ump"
public class Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextItemId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String itemName;
  private String description;
  private boolean canLoan;
  private int insuranceFee;
  private int loanFee;

  //Autounique Attributes
  private int itemId;

  //Item Associations
  private Loan loan;
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aItemName, String aDescription, boolean aCanLoan, int aInsuranceFee, int aLoanFee, MuseumManagement aMuseumManagement)
  {
    itemName = aItemName;
    description = aDescription;
    canLoan = aCanLoan;
    insuranceFee = aInsuranceFee;
    loanFee = aLoanFee;
    itemId = nextItemId++;
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create item due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setItemName(String aItemName)
  {
    boolean wasSet = false;
    itemName = aItemName;
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

  public String getItemName()
  {
    return itemName;
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

  public int getItemId()
  {
    return itemId;
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
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setLoan(Loan aNewLoan)
  {
    boolean wasSet = false;
    loan = aNewLoan;
    wasSet = true;
    return wasSet;
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
      existingMuseumManagement.removeItem(this);
    }
    museumManagement.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    loan = null;
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "itemId" + ":" + getItemId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "canLoan" + ":" + getCanLoan()+ "," +
            "insuranceFee" + ":" + getInsuranceFee()+ "," +
            "loanFee" + ":" + getLoanFee()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "loan = "+(getLoan()!=null?Integer.toHexString(System.identityHashCode(getLoan())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 91 "../../../../../mmss.ump"
// line 200 "../../../../../mmss.ump"
public class Donation extends Exchange
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextDonationId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Donation Attributes
  private String itemName;
  private String descripton;

  //Autounique Attributes
  private int donationId;

  //Donation Associations
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Donation(Date aSubmittedDate, boolean aApproved, String aItemName, String aDescripton, MuseumManagement aMuseumManagement)
  {
    super(aSubmittedDate, aApproved);
    itemName = aItemName;
    descripton = aDescripton;
    donationId = nextDonationId++;
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create donation due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setDescripton(String aDescripton)
  {
    boolean wasSet = false;
    descripton = aDescripton;
    wasSet = true;
    return wasSet;
  }

  public String getItemName()
  {
    return itemName;
  }

  public String getDescripton()
  {
    return descripton;
  }

  public int getDonationId()
  {
    return donationId;
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
      existingMuseumManagement.removeDonation(this);
    }
    museumManagement.addDonation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeDonation(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "donationId" + ":" + getDonationId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "descripton" + ":" + getDescripton()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}
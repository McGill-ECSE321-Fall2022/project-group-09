/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 72 "../../../../../mmss.ump"
// line 149 "../../../../../mmss.ump"
public class Donation extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Donation Attributes
  private String donationId;
  private String itemName;
  private String descripton;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Donation(Date aSubmittedDate, boolean aApproved, String aDonationId, String aItemName, String aDescripton)
  {
    super(aSubmittedDate, aApproved);
    donationId = aDonationId;
    itemName = aItemName;
    descripton = aDescripton;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDonationId(String aDonationId)
  {
    boolean wasSet = false;
    donationId = aDonationId;
    wasSet = true;
    return wasSet;
  }

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

  public String getDonationId()
  {
    return donationId;
  }

  public String getItemName()
  {
    return itemName;
  }

  public String getDescripton()
  {
    return descripton;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "donationId" + ":" + getDonationId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "descripton" + ":" + getDescripton()+ "]";
  }
}
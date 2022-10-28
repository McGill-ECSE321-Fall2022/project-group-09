/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 66 "../../../../../mmss.ump"
// line 167 "../../../../../mmss.ump"

@Entity
public class Donation extends Exchange
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Donation Attributes
  @Column(nullable = false)
  private String itemName;
  @Column(nullable = false)
  private String descripton;

  //Donation Associations
  
  @ManyToOne(optional = false)
  private Visitor visitor;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Donation () {}

  public Donation(int aExchangeId, Date aSubmittedDate, String aItemName, String aDescripton, Visitor aVisitor)
  {
    super(aExchangeId, aSubmittedDate);
    itemName = aItemName;
    descripton = aDescripton;
    if (!setVisitor(aVisitor))
    {
      throw new RuntimeException("Unable to create Donation due to aVisitor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Visitor getVisitor()
  {
    return visitor;
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
    visitor = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "itemName" + ":" + getItemName()+ "," +
            "descripton" + ":" + getDescripton()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "visitor = "+(getVisitor()!=null?Integer.toHexString(System.identityHashCode(getVisitor())):"null");
  }
}
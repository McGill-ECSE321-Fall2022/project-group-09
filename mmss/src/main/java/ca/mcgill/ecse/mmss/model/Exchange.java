/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


// line 55 "../../../../../../model.ump"
// line 202 "../../../../../../model.ump"

@MappedSuperclass
public abstract class Exchange
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ExchangeStatus { Pending, Declined, Approved }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exchange Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int exchangeId;
  private Date submittedDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected Exchange() {}

  public Exchange(int aExchangeId, Date aSubmittedDate)
  {
    exchangeId = aExchangeId;
    submittedDate = aSubmittedDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setExchangeId(int aExchangeId)
  {
    boolean wasSet = false;
    exchangeId = aExchangeId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSubmittedDate(Date aSubmittedDate)
  {
    boolean wasSet = false;
    submittedDate = aSubmittedDate;
    wasSet = true;
    return wasSet;
  }

  public int getExchangeId()
  {
    return exchangeId;
  }

  public Date getSubmittedDate()
  {
    return submittedDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "exchangeId" + ":" + getExchangeId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedDate" + "=" + (getSubmittedDate() != null ? !getSubmittedDate().equals(this)  ? getSubmittedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
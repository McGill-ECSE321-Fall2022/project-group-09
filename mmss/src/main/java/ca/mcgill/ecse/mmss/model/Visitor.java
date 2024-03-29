/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Column;
import javax.persistence.Entity;

// line 39 "../../../../../mmss.ump"
// line 122 "../../../../../mmss.ump"
// line 148 "../../../../../mmss.ump"

@Entity
public class Visitor extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  @Column(nullable = false)
  private double balance;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor () {}
  
  public Visitor(String aUsername, String aPassword, Person aPerson)
  {
    super(aUsername, aPassword, aPerson);
    balance = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBalance(double aBalance)
  {
    boolean wasSet = false;
    balance = aBalance;
    wasSet = true;
    return wasSet;
  }

  public double getBalance()
  {
    return balance;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "balance" + ":" + getBalance()+ "]";
  }
}
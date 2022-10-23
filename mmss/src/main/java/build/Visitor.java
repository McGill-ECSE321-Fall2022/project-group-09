/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 37 "mmss.ump"
// line 96 "mmss.ump"
// line 120 "mmss.ump"
public class Visitor extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private int balance;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aUsername, String aPassword, Person aPerson)
  {
    super(aUsername, aPassword, aPerson);
    balance = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBalance(int aBalance)
  {
    boolean wasSet = false;
    balance = aBalance;
    wasSet = true;
    return wasSet;
  }

  public int getBalance()
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
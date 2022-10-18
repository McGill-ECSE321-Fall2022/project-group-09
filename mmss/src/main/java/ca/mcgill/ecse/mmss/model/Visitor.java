/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;
import java.sql.Date;

// line 42 "../../../../../mmss.ump"
// line 113 "../../../../../mmss.ump"
// line 133 "../../../../../mmss.ump"
public class Visitor extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private int balance;

  //Visitor Associations
  private List<Loan> visitorLoans;
  private List<Donation> userDonations;
  private List<Ticket> tickets;
  private List<Tour> tours;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aUsername, String aPassword)
  {
    super(aUsername, aPassword);
    balance = 0;
    visitorLoans = new ArrayList<Loan>();
    userDonations = new ArrayList<Donation>();
    tickets = new ArrayList<Ticket>();
    tours = new ArrayList<Tour>();
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
  /* Code from template association_GetMany */
  public Loan getVisitorLoan(int index)
  {
    Loan aVisitorLoan = visitorLoans.get(index);
    return aVisitorLoan;
  }

  public List<Loan> getVisitorLoans()
  {
    List<Loan> newVisitorLoans = Collections.unmodifiableList(visitorLoans);
    return newVisitorLoans;
  }

  public int numberOfVisitorLoans()
  {
    int number = visitorLoans.size();
    return number;
  }

  public boolean hasVisitorLoans()
  {
    boolean has = visitorLoans.size() > 0;
    return has;
  }

  public int indexOfVisitorLoan(Loan aVisitorLoan)
  {
    int index = visitorLoans.indexOf(aVisitorLoan);
    return index;
  }
  /* Code from template association_GetMany */
  public Donation getUserDonation(int index)
  {
    Donation aUserDonation = userDonations.get(index);
    return aUserDonation;
  }

  public List<Donation> getUserDonations()
  {
    List<Donation> newUserDonations = Collections.unmodifiableList(userDonations);
    return newUserDonations;
  }

  public int numberOfUserDonations()
  {
    int number = userDonations.size();
    return number;
  }

  public boolean hasUserDonations()
  {
    boolean has = userDonations.size() > 0;
    return has;
  }

  public int indexOfUserDonation(Donation aUserDonation)
  {
    int index = userDonations.indexOf(aUserDonation);
    return index;
  }
  /* Code from template association_GetMany */
  public Ticket getTicket(int index)
  {
    Ticket aTicket = tickets.get(index);
    return aTicket;
  }

  public List<Ticket> getTickets()
  {
    List<Ticket> newTickets = Collections.unmodifiableList(tickets);
    return newTickets;
  }

  public int numberOfTickets()
  {
    int number = tickets.size();
    return number;
  }

  public boolean hasTickets()
  {
    boolean has = tickets.size() > 0;
    return has;
  }

  public int indexOfTicket(Ticket aTicket)
  {
    int index = tickets.indexOf(aTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public Tour getTour(int index)
  {
    Tour aTour = tours.get(index);
    return aTour;
  }

  public List<Tour> getTours()
  {
    List<Tour> newTours = Collections.unmodifiableList(tours);
    return newTours;
  }

  public int numberOfTours()
  {
    int number = tours.size();
    return number;
  }

  public boolean hasTours()
  {
    boolean has = tours.size() > 0;
    return has;
  }

  public int indexOfTour(Tour aTour)
  {
    int index = tours.indexOf(aTour);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitorLoans()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfVisitorLoans()
  {
    return 5;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addVisitorLoan(Loan aVisitorLoan)
  {
    boolean wasAdded = false;
    if (visitorLoans.contains(aVisitorLoan)) { return false; }
    if (numberOfVisitorLoans() < maximumNumberOfVisitorLoans())
    {
      visitorLoans.add(aVisitorLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeVisitorLoan(Loan aVisitorLoan)
  {
    boolean wasRemoved = false;
    if (visitorLoans.contains(aVisitorLoan))
    {
      visitorLoans.remove(aVisitorLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setVisitorLoans(Loan... newVisitorLoans)
  {
    boolean wasSet = false;
    ArrayList<Loan> verifiedVisitorLoans = new ArrayList<Loan>();
    for (Loan aVisitorLoan : newVisitorLoans)
    {
      if (verifiedVisitorLoans.contains(aVisitorLoan))
      {
        continue;
      }
      verifiedVisitorLoans.add(aVisitorLoan);
    }

    if (verifiedVisitorLoans.size() != newVisitorLoans.length || verifiedVisitorLoans.size() > maximumNumberOfVisitorLoans())
    {
      return wasSet;
    }

    visitorLoans.clear();
    visitorLoans.addAll(verifiedVisitorLoans);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVisitorLoanAt(Loan aVisitorLoan, int index)
  {  
    boolean wasAdded = false;
    if(addVisitorLoan(aVisitorLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitorLoans()) { index = numberOfVisitorLoans() - 1; }
      visitorLoans.remove(aVisitorLoan);
      visitorLoans.add(index, aVisitorLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVisitorLoanAt(Loan aVisitorLoan, int index)
  {
    boolean wasAdded = false;
    if(visitorLoans.contains(aVisitorLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitorLoans()) { index = numberOfVisitorLoans() - 1; }
      visitorLoans.remove(aVisitorLoan);
      visitorLoans.add(index, aVisitorLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVisitorLoanAt(aVisitorLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserDonations()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addUserDonation(Donation aUserDonation)
  {
    boolean wasAdded = false;
    if (userDonations.contains(aUserDonation)) { return false; }
    userDonations.add(aUserDonation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUserDonation(Donation aUserDonation)
  {
    boolean wasRemoved = false;
    if (userDonations.contains(aUserDonation))
    {
      userDonations.remove(aUserDonation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserDonationAt(Donation aUserDonation, int index)
  {  
    boolean wasAdded = false;
    if(addUserDonation(aUserDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserDonations()) { index = numberOfUserDonations() - 1; }
      userDonations.remove(aUserDonation);
      userDonations.add(index, aUserDonation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserDonationAt(Donation aUserDonation, int index)
  {
    boolean wasAdded = false;
    if(userDonations.contains(aUserDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserDonations()) { index = numberOfUserDonations() - 1; }
      userDonations.remove(aUserDonation);
      userDonations.add(index, aUserDonation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserDonationAt(aUserDonation, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    tickets.add(aTicket);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicket(Ticket aTicket)
  {
    boolean wasRemoved = false;
    if (tickets.contains(aTicket))
    {
      tickets.remove(aTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAt(Ticket aTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTicket(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAt(Ticket aTicket, int index)
  {
    boolean wasAdded = false;
    if(tickets.contains(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAt(aTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTours()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTour(Tour aTour)
  {
    boolean wasAdded = false;
    if (tours.contains(aTour)) { return false; }
    tours.add(aTour);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTour(Tour aTour)
  {
    boolean wasRemoved = false;
    if (tours.contains(aTour))
    {
      tours.remove(aTour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTourAt(Tour aTour, int index)
  {  
    boolean wasAdded = false;
    if(addTour(aTour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTours()) { index = numberOfTours() - 1; }
      tours.remove(aTour);
      tours.add(index, aTour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTourAt(Tour aTour, int index)
  {
    boolean wasAdded = false;
    if(tours.contains(aTour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTours()) { index = numberOfTours() - 1; }
      tours.remove(aTour);
      tours.add(index, aTour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTourAt(aTour, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    visitorLoans.clear();
    userDonations.clear();
    tickets.clear();
    tours.clear();
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "balance" + ":" + getBalance()+ "]";
  }
}
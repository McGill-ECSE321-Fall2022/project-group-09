/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import java.util.*;

// line 3 "../../../../../mmss.ump"
// line 144 "../../../../../mmss.ump"
public class MuseumManagement
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MuseumManagement Attributes
  private Date date;

  //MuseumManagement Associations
  private Manager manager;
  private List<Visitor> visitors;
  private List<Employee> employees;
  private List<User> users;
  private List<WorkDay> workingDays;
  private List<Room> rooms;
  private List<Ticket> tickets;
  private List<Tour> tours;
  private List<Item> items;
  private List<Loan> loans;
  private List<Donation> donations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MuseumManagement(Date aDate)
  {
    date = aDate;
    visitors = new ArrayList<Visitor>();
    employees = new ArrayList<Employee>();
    users = new ArrayList<User>();
    workingDays = new ArrayList<WorkDay>();
    rooms = new ArrayList<Room>();
    tickets = new ArrayList<Ticket>();
    tours = new ArrayList<Tour>();
    items = new ArrayList<Item>();
    loans = new ArrayList<Loan>();
    donations = new ArrayList<Donation>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }

  public boolean hasManager()
  {
    boolean has = manager != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Visitor getVisitor(int index)
  {
    Visitor aVisitor = visitors.get(index);
    return aVisitor;
  }

  public List<Visitor> getVisitors()
  {
    List<Visitor> newVisitors = Collections.unmodifiableList(visitors);
    return newVisitors;
  }

  public int numberOfVisitors()
  {
    int number = visitors.size();
    return number;
  }

  public boolean hasVisitors()
  {
    boolean has = visitors.size() > 0;
    return has;
  }

  public int indexOfVisitor(Visitor aVisitor)
  {
    int index = visitors.indexOf(aVisitor);
    return index;
  }
  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public WorkDay getWorkingDay(int index)
  {
    WorkDay aWorkingDay = workingDays.get(index);
    return aWorkingDay;
  }

  public List<WorkDay> getWorkingDays()
  {
    List<WorkDay> newWorkingDays = Collections.unmodifiableList(workingDays);
    return newWorkingDays;
  }

  public int numberOfWorkingDays()
  {
    int number = workingDays.size();
    return number;
  }

  public boolean hasWorkingDays()
  {
    boolean has = workingDays.size() > 0;
    return has;
  }

  public int indexOfWorkingDay(WorkDay aWorkingDay)
  {
    int index = workingDays.indexOf(aWorkingDay);
    return index;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
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
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }
  /* Code from template association_GetMany */
  public Donation getDonation(int index)
  {
    Donation aDonation = donations.get(index);
    return aDonation;
  }

  public List<Donation> getDonations()
  {
    List<Donation> newDonations = Collections.unmodifiableList(donations);
    return newDonations;
  }

  public int numberOfDonations()
  {
    int number = donations.size();
    return number;
  }

  public boolean hasDonations()
  {
    boolean has = donations.size() > 0;
    return has;
  }

  public int indexOfDonation(Donation aDonation)
  {
    int index = donations.indexOf(aDonation);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (manager != null && !manager.equals(aNewManager) && equals(manager.getMuseumManagement()))
    {
      //Unable to setManager, as existing manager would become an orphan
      return wasSet;
    }

    manager = aNewManager;
    MuseumManagement anOldMuseumManagement = aNewManager != null ? aNewManager.getMuseumManagement() : null;

    if (!this.equals(anOldMuseumManagement))
    {
      if (anOldMuseumManagement != null)
      {
        anOldMuseumManagement.manager = null;
      }
      if (manager != null)
      {
        manager.setMuseumManagement(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Visitor addVisitor(String aUsername, String aPassword)
  {
    return new Visitor(aUsername, aPassword, this);
  }

  public boolean addVisitor(Visitor aVisitor)
  {
    boolean wasAdded = false;
    if (visitors.contains(aVisitor)) { return false; }
    MuseumManagement existingMuseumManagement = aVisitor.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aVisitor.setMuseumManagement(this);
    }
    else
    {
      visitors.add(aVisitor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVisitor(Visitor aVisitor)
  {
    boolean wasRemoved = false;
    //Unable to remove aVisitor, as it must always have a museumManagement
    if (!this.equals(aVisitor.getMuseumManagement()))
    {
      visitors.remove(aVisitor);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVisitorAt(Visitor aVisitor, int index)
  {  
    boolean wasAdded = false;
    if(addVisitor(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVisitorAt(Visitor aVisitor, int index)
  {
    boolean wasAdded = false;
    if(visitors.contains(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVisitorAt(aVisitor, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfEmployees()
  {
    return 20;
  }
  /* Code from template association_AddOptionalNToOne */
  public Employee addEmployee(String aUsername, String aPassword)
  {
    if (numberOfEmployees() >= maximumNumberOfEmployees())
    {
      return null;
    }
    else
    {
      return new Employee(aUsername, aPassword, this);
    }
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    if (numberOfEmployees() >= maximumNumberOfEmployees())
    {
      return wasAdded;
    }

    MuseumManagement existingMuseumManagement = aEmployee.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aEmployee.setMuseumManagement(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a museumManagement
    if (!this.equals(aEmployee.getMuseumManagement()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aFirstName, String aLastName, AccountType... allAccountTypes)
  {
    return new User(aFirstName, aLastName, this, allAccountTypes);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    MuseumManagement existingMuseumManagement = aUser.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aUser.setMuseumManagement(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a museumManagement
    if (!this.equals(aUser.getMuseumManagement()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkingDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addWorkingDay(Date aWorkingDate)
  {
    return new WorkDay(aWorkingDate, this);
  }

  public boolean addWorkingDay(WorkDay aWorkingDay)
  {
    boolean wasAdded = false;
    if (workingDays.contains(aWorkingDay)) { return false; }
    MuseumManagement existingMuseumManagement = aWorkingDay.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aWorkingDay.setMuseumManagement(this);
    }
    else
    {
      workingDays.add(aWorkingDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkingDay(WorkDay aWorkingDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aWorkingDay, as it must always have a museumManagement
    if (!this.equals(aWorkingDay.getMuseumManagement()))
    {
      workingDays.remove(aWorkingDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkingDayAt(WorkDay aWorkingDay, int index)
  {  
    boolean wasAdded = false;
    if(addWorkingDay(aWorkingDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkingDays()) { index = numberOfWorkingDays() - 1; }
      workingDays.remove(aWorkingDay);
      workingDays.add(index, aWorkingDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkingDayAt(WorkDay aWorkingDay, int index)
  {
    boolean wasAdded = false;
    if(workingDays.contains(aWorkingDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkingDays()) { index = numberOfWorkingDays() - 1; }
      workingDays.remove(aWorkingDay);
      workingDays.add(index, aWorkingDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkingDayAt(aWorkingDay, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Room addRoom(String aRoomID)
  {
    return new Room(aRoomID, this);
  }

  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    MuseumManagement existingMuseumManagement = aRoom.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aRoom.setMuseumManagement(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a museumManagement
    if (!this.equals(aRoom.getMuseumManagement()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addTicket(Date aDate, int aPrice)
  {
    return new Ticket(aDate, aPrice, this);
  }

  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    MuseumManagement existingMuseumManagement = aTicket.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aTicket.setMuseumManagement(this);
    }
    else
    {
      tickets.add(aTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicket(Ticket aTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicket, as it must always have a museumManagement
    if (!this.equals(aTicket.getMuseumManagement()))
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
  /* Code from template association_AddManyToOne */
  public Tour addTour(Date aDate, int aPrice)
  {
    return new Tour(aDate, aPrice, this);
  }

  public boolean addTour(Tour aTour)
  {
    boolean wasAdded = false;
    if (tours.contains(aTour)) { return false; }
    MuseumManagement existingMuseumManagement = aTour.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aTour.setMuseumManagement(this);
    }
    else
    {
      tours.add(aTour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTour(Tour aTour)
  {
    boolean wasRemoved = false;
    //Unable to remove aTour, as it must always have a museumManagement
    if (!this.equals(aTour.getMuseumManagement()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Item addItem(String aItemName, String aDescription, boolean aCanLoan, int aInsuranceFee, int aLoanFee)
  {
    return new Item(aItemName, aDescription, aCanLoan, aInsuranceFee, aLoanFee, this);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    MuseumManagement existingMuseumManagement = aItem.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aItem.setMuseumManagement(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a museumManagement
    if (!this.equals(aItem.getMuseumManagement()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(Date aSubmittedDate, boolean aApproved, Date aDueDate)
  {
    return new Loan(aSubmittedDate, aApproved, aDueDate, this);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    MuseumManagement existingMuseumManagement = aLoan.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aLoan.setMuseumManagement(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a museumManagement
    if (!this.equals(aLoan.getMuseumManagement()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Donation addDonation(Date aSubmittedDate, boolean aApproved, String aItemName, String aDescripton)
  {
    return new Donation(aSubmittedDate, aApproved, aItemName, aDescripton, this);
  }

  public boolean addDonation(Donation aDonation)
  {
    boolean wasAdded = false;
    if (donations.contains(aDonation)) { return false; }
    MuseumManagement existingMuseumManagement = aDonation.getMuseumManagement();
    boolean isNewMuseumManagement = existingMuseumManagement != null && !this.equals(existingMuseumManagement);
    if (isNewMuseumManagement)
    {
      aDonation.setMuseumManagement(this);
    }
    else
    {
      donations.add(aDonation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDonation(Donation aDonation)
  {
    boolean wasRemoved = false;
    //Unable to remove aDonation, as it must always have a museumManagement
    if (!this.equals(aDonation.getMuseumManagement()))
    {
      donations.remove(aDonation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDonationAt(Donation aDonation, int index)
  {  
    boolean wasAdded = false;
    if(addDonation(aDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDonations()) { index = numberOfDonations() - 1; }
      donations.remove(aDonation);
      donations.add(index, aDonation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDonationAt(Donation aDonation, int index)
  {
    boolean wasAdded = false;
    if(donations.contains(aDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDonations()) { index = numberOfDonations() - 1; }
      donations.remove(aDonation);
      donations.add(index, aDonation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDonationAt(aDonation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Manager existingManager = manager;
    manager = null;
    if (existingManager != null)
    {
      existingManager.delete();
      existingManager.setMuseumManagement(null);
    }
    while (visitors.size() > 0)
    {
      Visitor aVisitor = visitors.get(visitors.size() - 1);
      aVisitor.delete();
      visitors.remove(aVisitor);
    }
    
    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }
    
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (workingDays.size() > 0)
    {
      WorkDay aWorkingDay = workingDays.get(workingDays.size() - 1);
      aWorkingDay.delete();
      workingDays.remove(aWorkingDay);
    }
    
    while (rooms.size() > 0)
    {
      Room aRoom = rooms.get(rooms.size() - 1);
      aRoom.delete();
      rooms.remove(aRoom);
    }
    
    while (tickets.size() > 0)
    {
      Ticket aTicket = tickets.get(tickets.size() - 1);
      aTicket.delete();
      tickets.remove(aTicket);
    }
    
    while (tours.size() > 0)
    {
      Tour aTour = tours.get(tours.size() - 1);
      aTour.delete();
      tours.remove(aTour);
    }
    
    while (items.size() > 0)
    {
      Item aItem = items.get(items.size() - 1);
      aItem.delete();
      items.remove(aItem);
    }
    
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
    while (donations.size() > 0)
    {
      Donation aDonation = donations.get(donations.size() - 1);
      aDonation.delete();
      donations.remove(aDonation);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}
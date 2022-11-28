package ca.mcgill.ecse.mmss.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.TourRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
@Configuration
public class Util {

    @Autowired 
    ArtefactRepository artefactRepository;

    @Autowired
    CommunicationRepository communicationRepository;

    @Autowired
    DonationRepository donationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

   @Autowired
    LoanRepository loanRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    OpenDayRepository openDayRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired 
    ScheduleRepository scheduleRepository;

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @Autowired
    TourRepository tourRepository;
    
    
    public void clearDatabase() {

        loanRepository.deleteAll();
        donationRepository.deleteAll();
        tourRepository.deleteAll();
        ticketRepository.deleteAll();
        notificationRepository.deleteAll();
        artefactRepository.deleteAll();
        roomRepository.deleteAll();
        employeeRepository.deleteAll();
        managerRepository.deleteAll();
        visitorRepository.deleteAll();
        openDayRepository.deleteAll();
        shiftRepository.deleteAll();
        scheduleRepository.deleteAll();
        communicationRepository.deleteAll();
        personRepository.deleteAll();

    } 
    
}

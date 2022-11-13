package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Person;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CommunicationRepository communicationRepository; 


    @Transactional
    public Manager getManager() { 
        Manager manager = managerRepository.findManagerByUsername("marwan.kanaan@mcgill.ca"); 
        return manager; 
    }
    /**
     * Run once when the system starts to create the manager
     * 
     * @author Shidan Javaheri
     * @return the manager
     */
    @Transactional
    public Manager createManager() {
        // create person
        Person marwan = new Person();
        marwan.setFirstName("Marwan");
        marwan.setLastName("kanaan");
        personRepository.save(marwan);

        // a visitor is always created with a communication
        Communication communication = new Communication(); 
        communicationRepository.save(communication); 
        // create manager with a communication
        Manager manager = new Manager("marwan.kanaan@mcgill.ca", "aVerySecurePassword", marwan);
        
        manager.setCommunication(communication); 
        managerRepository.save(manager);

        return manager;

    }

    /**
     * Update the managers password
     * 
     * @author Shidan Javaheri
     * @param currentPassword
     * @param newPassword
     * @return the manager
     */
    @Transactional
    public Manager updateMangagerPassword(String currentPassword, String newPassword) {
        // check if current password matches
        String username = "marwan.kanaan@mcgill.ca";
        Manager manager = managerRepository.findManagerByUsername(username);
        String password = manager.getPassword();
        
        // if the passwords don't match
        if (currentPassword.compareTo(password) != 0) {
            throw new MmssException(HttpStatus.BAD_REQUEST, "Incorrect password");

        } else {
            manager.setPassword(newPassword);
            managerRepository.save(manager);
        }
        return manager;
    }

 
}

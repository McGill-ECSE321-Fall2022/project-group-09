package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.OpenDay;

@Service
public class OpenDayService {
	
	@Autowired
	private OpenDayRepository openDayRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;
	
	/**
     * Finds an OpenDay by its Date
     * 
     * @author Mohamed Elsamadouny
     * @param Date
     * @return the OpenDay with the specific date
     */
    @Transactional
    public OpenDay getOpenDayByDate(Date date) {

        // use the repository method
        OpenDay openDay = openDayRepository.findOpenDayByDate(date);
        if (openDay == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "OpenDay not found");
        }
        return openDay;
    }
    
    /**
     * 
     * Finds all the openDays in the database
     * 
     * @author Mohamed Elsamadouny
     * @return an arraylist of OpenDays
     */
    @Transactional
    public ArrayList<OpenDay> getAllOpenDays() {

        // use repository method
        ArrayList<OpenDay> openDays = openDayRepository.findAll();
        

        return openDays;
    }

    /**
     * 
     * Return 7 days after a given date
     * 
     * @author Mohamed Elsamadouny
     * @return an arraylist of OpenDays
     */
    @Transactional
    public OpenDay calculateLoanDueDate(Date date) {

        // use repository method
        ArrayList<OpenDay> openDays = openDayRepository.findByDateGreaterThan(date);

        // sort the openDays by date
        openDays.sort(new ComparatorForOpenDay ()); 

        // wat to do if it doesnt exist?
        if (openDays.size() < 7) {
            throw new MmssException(HttpStatus.BAD_REQUEST, "Not enough OpenDays, please contact the manager");
        }
        // get the 6th index
        OpenDay dueDate = openDays.get(6);
        
        return dueDate;
    }
    
    /**
     * 
     * This method creates a new OpenDay with a given date
     * 
     * @author Mohamed Elsamadouny
     * @return an OpenDay object
     */
    public OpenDay createOpenDay(Date date) {
    	// create new openDay
    	OpenDay openDay = new OpenDay();
    	openDay.setDate(date);

        // set the scheduale
        openDay.setSchedule(scheduleService.getSchedule()); 

        // save the openDay
        openDayRepository.save(openDay);
    	return openDay;
    }    

    public class ComparatorForOpenDay implements Comparator<OpenDay> {
    @Override
    public int compare(OpenDay openDay1, OpenDay openDay2) {
        return openDay1.getDate().compareTo(openDay2.getDate());
    }
}
}

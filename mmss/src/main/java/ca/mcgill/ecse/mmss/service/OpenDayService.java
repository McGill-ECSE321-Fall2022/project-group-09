package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.OpenDay;

@Service
public class OpenDayService {
	
	@Autowired
	private OpenDayRepository openDayRepository;
	
	/**
     * Finds an OpenDay by its Date
     * 
     * @author Mohamed Elsamadouny
     * @param Date
     * @return the OpenDay with the specific date
     */
    @Transactional
    public OpenDay retrieveOpenDayByDate(Date date) {

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

        // wat to do if it doesnt exist?

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

        // save the openDay
        openDayRepository.save(openDay);
    	return openDay;
    }


    
    /**
     * Deletes an OpenDay with a specific day if it exists
     * 
     * @author Mohamed Elsamadouny
     * @param date
     */
    @Transactional
    public void deleteOpenDay(Date date) {

        OpenDay openDay = openDayRepository.findOpenDayByDate(date);
        if (openDay == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The openDat with this date was not found");

        // calls the repository to delete the date
        openDayRepository.deleteById(date);
    }
    
}

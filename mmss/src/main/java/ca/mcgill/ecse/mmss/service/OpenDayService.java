package ca.mcgill.ecse.mmss.service;

import java.sql.Date;

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
    public OpenDay getOpenDayByDate(Date date) {

        // use the repository method
        OpenDay openDay = openDayRepository.findOpenDayByDate(date);
        if (openDay == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "OpenDay not found");
        }
        return openDay;
    }
}

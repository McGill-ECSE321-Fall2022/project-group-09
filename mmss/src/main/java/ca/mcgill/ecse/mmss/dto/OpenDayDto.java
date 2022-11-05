package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;

import ca.mcgill.ecse.mmss.model.OpenDay;

public class OpenDayDto {
    private Date date; 
    private ScheduleDto schedule;
    /**
     * @author Shidan Javaheri
     * Constructor with schedule as an argument
     * @param schedule
     */ 
    public OpenDayDto (OpenDay openDay) { 
        this.date = openDay.getDate();
        this.schedule = new ScheduleDto(openDay.getSchedule().getScheduleId());
   }


    /**
     * Generic Constructor
     * @param date
     * @param schedule
     */
    public OpenDayDto(Date date, ScheduleDto schedule) {
    this.date = date;
    this.schedule = schedule;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public ScheduleDto getSchedule() {
        return schedule;
    }
    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    } 

   
   
   
}

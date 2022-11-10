package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;
import ca.mcgill.ecse.mmss.model.OpenDay;

public class OpenDayDto {
	private Date date;
	private int scheduleId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public OpenDayDto() {}
	
	/**
	 * Constructor with schedule as an argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param schedule
	 */
	public OpenDayDto(OpenDay openDay) {
		this.date = openDay.getDate();
		this.scheduleId = openDay.getSchedule().getScheduleId();
	}

	/**
	 * Generic Constructor
	 * 
	 * @param date
	 * @param scheduleId
	 * @author Shidan Javaheri, Shyam Desai
	 */
	public OpenDayDto(Date date, int scheduleId) {
		this.date = date;
		this.scheduleId = scheduleId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
}

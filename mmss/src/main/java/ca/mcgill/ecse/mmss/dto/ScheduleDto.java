package ca.mcgill.ecse.mmss.dto;

public class ScheduleDto {
	private int scheduleId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public ScheduleDto() {}
	
	/**
	 * Constructor for a ScheduleDto
	 * 
	 * @author Shidan Javaheri
	 * @param scheduleId
	 */
	public ScheduleDto(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
}

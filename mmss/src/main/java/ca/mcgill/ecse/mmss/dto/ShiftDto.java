package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

public class ShiftDto {
	private int shiftId;
	private ShiftTime shiftTime;
	private int scheduleId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public ShiftDto() {}
	
	/**
	 * Constructor that takes a shift as the argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param shift
	 */
	public ShiftDto(Shift shift) {
		this.shiftId = shift.getShiftId();
		this.shiftTime = shift.getShiftTime();
		this.scheduleId = shift.getSchedule().getScheduleId();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param shiftId
	 * @param shiftTime
	 * @param scheduleId
	 */
	public ShiftDto(int shiftId, ShiftTime shiftTime, int scheduleId) {
		this.shiftId = shiftId;
		this.shiftTime = shiftTime;
		this.scheduleId = scheduleId;
	}

	public int getShiftId() {
		return shiftId;
	}

	public ShiftTime getShiftTime() {
		return shiftTime;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public void setShiftTime(ShiftTime shiftTime) {
		this.shiftTime = shiftTime;
	}
}

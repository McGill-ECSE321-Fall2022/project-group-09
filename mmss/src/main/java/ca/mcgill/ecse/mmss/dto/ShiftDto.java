package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

public class ShiftDto {
    private int shiftId; 
    private ShiftTime shiftTime; 
    private ScheduleDto scheduleDto;

    /**
     * @author Shidan Javaheri
     * Constructor that takes a shift as the argument
     * @param shift
     */
    public ShiftDto ( Shift shift) { 
        this.shiftId = shift.getShiftId();
        this.shiftTime = shift.getShiftTime();
        this.scheduleDto = new ScheduleDto(shift.getSchedule().getScheduleId());       
    }
    
    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguments
     * @param shiftId
     * @param shiftTime
     * @param scheduleDto
     */
    public ShiftDto(int shiftId, ShiftTime shiftTime, ScheduleDto scheduleDto) {
        this.shiftId = shiftId;
        this.shiftTime = shiftTime;
        this.scheduleDto = scheduleDto;
    }
    public int getShiftId() {
        return shiftId;
    }
    public ShiftTime getShiftTime() {
        return shiftTime;
    }
    public ScheduleDto getScheduleDto() {
        return scheduleDto;
    } 

    
    
}

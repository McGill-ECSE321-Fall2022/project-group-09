package ca.mcgill.ecse.mmss.dto;

public class ScheduleDto {
    private int scheduleId;
    
    /**
     * @author Shidan Javaheri
     * Constructor for a ScheduleDto
     * @param scheduleId
     */
    public ScheduleDto(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    } 
    

    
}

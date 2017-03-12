package ucsbCurriculum.Utility;

public class Time {
    public int startTime;
    public int endTime;
    public int startHour;
    public int endHour;
    
    public Time(int start, int end) {
        startTime = start;
        endTime = end;
        
        int day = start/(24);
        start = start - (day*24*60);
        startHour = start/60;
        
        end = end - (day*60*24);
        endHour = end/60;
        
    }
    
    public int getStart() {
        return startTime;
    }
    
    public int getEnd() {
        return endTime;
    }
    
    public int getStartHour(){
    	return startHour;
    }
    
    public int getEndHour(){
    	return endHour;
    }
}
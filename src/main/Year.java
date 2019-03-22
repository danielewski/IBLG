package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Year implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<Week> weeks;
	int year;
	
	public Year(int year) {
		this.year = year;
		weeks = new ArrayList<Week>();
	}
	
	public String toString() {
		return String.valueOf(year);
	}
	
	public double totalHoursWorked() {
		double output = 0;
		
		for(Week week : weeks) {
			output += week.hoursWorked;
		}
		
		return output;
	}
	
	public double totalSickHoursEarned() {
		double output = 0;
		
		for(Week week : weeks) {
			output += week.sickHoursEarned();
		}
		
		if(output > 40) return 40;
		
		return output;
	}
	
	public double totalSickHoursUsed() {
		double output = 0;
		
		for(Week week : weeks) {
			output += week.sickHoursUsed;
		}
		
		if(output > 40) return 40;
		
		return output;
	}
	
	public double totalSickHoursLeft() {
		
		double hoursEarned = 0;
		for(Week week : weeks) {
			hoursEarned += week.sickHoursEarned();
		}
		
		if(hoursEarned - totalSickHoursUsed() > 40) return 40;
		
		return totalSickHoursEarned() - totalSickHoursUsed();
	}
}

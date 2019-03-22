package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Hours implements Serializable {

	private static final long serialVersionUID = 1L;
	ArrayList<Year> years;
	
	public Hours() {
		years = new ArrayList<Year>();
	}
	
	public double getTotalHoursWorked() {
		double output = 0;
		
		for(Year year : years) {
			for(Week week : year.weeks) {
				output += week.hoursWorked;
			}
		}
		return output;
	}
}

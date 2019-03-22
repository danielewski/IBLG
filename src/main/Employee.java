package main;

import java.io.Serializable;

public class Employee implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public String name;

	public Hours hours;

	public Employee(String name) {
		this.name = name;
		hours = new Hours();
	}

	public String toString() {
		return name;
	}

	public double totalSickHoursEarned() {
		double sum = 0;
		for(Year year : hours.years) {
			for(Week week : year.weeks) {
				sum += week.sickHoursEarned();
			}
		}

		return sum;
	}
	
	public double totalSickHoursRemaining() {
		double sum = totalSickHoursEarned();
		
		for(Year year : hours.years) {
			for(Week week : year.weeks) {
				sum -= week.sickHoursUsed;
			}
		}
		
		return sum;
	}
}

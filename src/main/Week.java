package main;

import java.io.Serializable;

public class Week implements Serializable {

	private static final long serialVersionUID = 1L;
	public double hoursWorked;
	public double bonusSickHours;
	public double sickHoursUsed;
	
	public int month;
	public int day;




	public Week(double hoursWorked, double bonusSickHours, int month, int day) {
		this.hoursWorked = hoursWorked;
		this.bonusSickHours = bonusSickHours;
		this.month = month;
		this.day = day;
	}

	public void addWorkHours(double hours) {
		hoursWorked += hours;

	}

	public void addBonusSickHours(double hours) {
		bonusSickHours += hours;

	}

	public void useSickHours(double hours) {
		sickHoursUsed += hours;
	}

	public double sickHoursEarned() {
		return hoursWorked / 30.0;
	}

	public double sickHoursRemaining() {
		return sickHoursEarned() - sickHoursUsed;
	}
	
	public String toString() {
		return String.valueOf(month) + "/" + String.valueOf(day);
	}
}

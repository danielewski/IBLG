package main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;


public class Exporter {
	PrintWriter out = new PrintWriter("Employee List.txt");
	public Exporter (DefaultListModel<Employee> employees, int year) throws FileNotFoundException{
		initialize(year);
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File("Employee List.txt");
		        int x = employees.size();
		        for (int y = 0; y < x; y++){
		        	retriever(employees.get(y), year);
		        }
		        out.close();
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        // no application registered for PDFs
		    }
		    }
	}
	public void initialize(int year) throws FileNotFoundException{
		out.println("Employee List for: " +year);
		out.println();
	}
	
	
	public void retriever (Employee employee, int year) throws FileNotFoundException{
		  out.println(employee.name + ":");
		  out.println("\tHours Worked: " + employee.hours.years.get(year).totalHoursWorked());
		  out.println("\tSick Hours Earned: " + employee.hours.years.get(year).totalSickHoursEarned());
		  out.println("\tSick Hours Used: " + employee.hours.years.get(year).totalSickHoursUsed());
		  out.println("\tSick Hours Left: " + employee.hours.years.get(year).totalSickHoursLeft());
		  
	}
	
}

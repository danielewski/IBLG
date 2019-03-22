package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainWindow {

	private JFrame frame;
	private JTextField newEmployeeNameTextField;

	private DefaultListModel<Employee> listModel = new DefaultListModel<Employee>();
	private JLabel employeeNameLabel;
	private JList<Employee> employeeList;

	private Employee selectedEmployee = null;
	private int selectedIndex = -1;
	private Week selectedWeek = null;
	private Year selectedYear = null;

	private JLabel sickHoursEarnedLabel;
	private JLabel hoursWorkedLabel;
	private JLabel sickHoursUsedLabel;
	private JLabel sickHoursRemainingLabel;
	private JLabel copyrightLabel;
	private JLabel logo;
	private JLabel disclaimerLabel;

	private int width = 720;
	private int length = 492;

	private JComboBox<Year> yearComboBox;

	private JComboBox<Week> weekComboBox;
	private JTextField addHoursWorkedTextField;
	private JTextField addSickHoursUsedTextFeild;
	private JLabel sickHoursUsedInSelectedYear;
	private JButton btnAddYears;
	private JButton btnAddWeeks;
	private JButton addHoursWorkedButton;
	private JButton addSickHoursUsedButton;
	private JButton btnExportToDocument;
	private JButton editEmployeeButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public void returnFromEditEmployee(Employee editedEmployee) {		
		if(editedEmployee == null) {
			return;
		}

		listModel.setElementAt(editedEmployee, selectedIndex);

		employeeSelected(selectedIndex);
	}

	public void deleteEmployee(Employee employee) {
		listModel.removeElement(employee);

		selectedIndex = -1;

		updateComboBoxes();
		updateAllEmployeeLabels();
	}
	
	public void addYear(Year newYear) {
		selectedEmployee.hours.years.add(newYear);
		yearComboBox.addItem(newYear);
		yearComboBox.setSelectedItem(newYear);
		selectedYear = newYear;
		
		updateWeeksComboBox();
		updateAllEmployeeLabels();
	}
	
	public void addWeek(Week newWeek) {
		selectedYear.weeks.add(newWeek);
		weekComboBox.addItem(newWeek);
		weekComboBox.setSelectedItem(newWeek);
		selectedWeek = newWeek;
		
		updateWeekSensitiveEmployeeLabels();
	}

	public void updateComboBoxes() {
		
		if(selectedIndex == -1) {
			yearComboBox.removeAllItems();
			weekComboBox.removeAllItems();
			return;
		}

		selectedEmployee = listModel.getElementAt(selectedIndex);

		yearComboBox.removeAllItems();
		for(Year year : selectedEmployee.hours.years) {
			yearComboBox.addItem(year);
		}

		selectedYear = yearComboBox.getItemAt(yearComboBox.getSelectedIndex());

		weekComboBox.removeAllItems();

		if(selectedYear != null) {
			for(Week week : selectedYear.weeks) {
				weekComboBox.addItem(week);
			}
		}
		
		selectedWeek = weekComboBox.getItemAt(weekComboBox.getSelectedIndex());
	}

	public void updateWeeksComboBox() {		
		weekComboBox.removeAllItems();

		if(selectedYear != null) {
			for(Week week : selectedYear.weeks) {
				weekComboBox.addItem(week);
			}
		}
		selectedWeek = weekComboBox.getItemAt(weekComboBox.getSelectedIndex());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveEmployees();
			}
		});
		frame.setBounds(100, 100, 800, 492);
		frame.setTitle("Massachusetts Earned Sick Time Tracker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		newEmployeeNameTextField = new JTextField();
		newEmployeeNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					addEmployeeButtonPressed();
				}
			}
		});
		newEmployeeNameTextField.setBounds(192, 13, 147, 20);
		frame.getContentPane().add(newEmployeeNameTextField);
		newEmployeeNameTextField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 147, 287);
		frame.getContentPane().add(scrollPane);


		listModel = loadEmployees();

		employeeList = new JList<Employee>(listModel);
		scrollPane.setViewportView(employeeList);
		employeeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				employeeSelected(employeeList.getSelectedIndex());
			}
		});
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addEmployeeButtonPressed();
			}
		});
		addEmployeeButton.setBounds(346, 11, 125, 23);
		frame.getContentPane().add(addEmployeeButton);

		editEmployeeButton = new JButton("Edit Employee Information");
		editEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editEmployeeButtonPressed();
			}
		});
		editEmployeeButton.setBounds(271, 384, 200, 23);
		frame.getContentPane().add(editEmployeeButton);

		JLabel lblEmployeeName = new JLabel("New Employee Name:");
		lblEmployeeName.setBounds(10, 13, 180, 17);
		lblEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblEmployeeName);

		JLabel lblEmployeName = new JLabel("Select Employee:");
		lblEmployeName.setBounds(10, 68, 135, 20);
		lblEmployeName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblEmployeName);

		employeeNameLabel = new JLabel("");
		employeeNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		employeeNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeNameLabel.setBounds(148, 68, 349, 20);
		frame.getContentPane().add(employeeNameLabel);

		JLabel hoursWorkedDescriptionLabel = new JLabel("Hours Worked In Selected Week:");
		hoursWorkedDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		hoursWorkedDescriptionLabel.setBounds(192, 308, 280, 14);
		frame.getContentPane().add(hoursWorkedDescriptionLabel);

		JLabel lblSickHoursEarned = new JLabel("Sick Hours Earned In Selected Year:");
		lblSickHoursEarned.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSickHoursEarned.setBounds(192, 216, 294, 14);
		frame.getContentPane().add(lblSickHoursEarned);

		sickHoursEarnedLabel = new JLabel("0");
		sickHoursEarnedLabel.setBounds(530, 216, 174, 14);
		sickHoursEarnedLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(sickHoursEarnedLabel);

		hoursWorkedLabel = new JLabel("0");
		hoursWorkedLabel.setBounds(530, 308, 60, 14);
		hoursWorkedLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(hoursWorkedLabel);

		JLabel lblSickHoursUsed = new JLabel("Sick Hours Used In Selected Week:");
		lblSickHoursUsed.setBounds(193, 333, 278, 14);
		lblSickHoursUsed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblSickHoursUsed);

		JLabel lblSickHoursRemaining = new JLabel("Sick Hours Remaining In Selected Year:");
		lblSickHoursRemaining.setBounds(192, 259, 328, 25);
		lblSickHoursRemaining.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblSickHoursRemaining);

		sickHoursUsedLabel = new JLabel("0");
		sickHoursUsedLabel.setBounds(530, 333, 60, 14);
		sickHoursUsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(sickHoursUsedLabel);

		sickHoursRemainingLabel = new JLabel("0");
		sickHoursRemainingLabel.setBounds(530, 264, 174, 14);
		sickHoursRemainingLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(sickHoursRemainingLabel);

		copyrightLabel = new JLabel ("\u00a9 2015 Innovative Business Law Group, PC");
		copyrightLabel.setBounds(5, length-50, 260, 14);
		frame.getContentPane().add(copyrightLabel);

		disclaimerLabel = new JLabel ("<HTML><U>Disclaimer and Licensing Information</U></HTML>");
		disclaimerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				disclaimerLabelPressed();
			}
		});
		disclaimerLabel.setBounds(width-150, length-50, 260, 14);
		disclaimerLabel.setForeground(Color.BLUE);
		frame.getContentPane().add(disclaimerLabel);

		//displays logo
		ImageIcon IBLGlogo = new ImageIcon(MainWindow.class.getResource("/IBLGlogo.png"));//"src/res/IBLGlogo.png");
		logo = new JLabel(IBLGlogo);
		logo.setBounds(507,13, 248 , 62);
		frame.getContentPane().add(logo);

		yearComboBox = new JComboBox<Year>();
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Something selected in the year box.
				selectedYear = yearComboBox.getItemAt(yearComboBox.getSelectedIndex());
				updateWeeksComboBox();
				updateNonWeekSensitiveEmployeeLabels();
				updateWeekSensitiveEmployeeLabels();
				updateButtons();
			}
		});
		yearComboBox.setBounds(192, 123, 101, 20);
		frame.getContentPane().add(yearComboBox);

		weekComboBox = new JComboBox<Week>();
		weekComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedWeek = weekComboBox.getItemAt(weekComboBox.getSelectedIndex());
				updateWeekSensitiveEmployeeLabels();
				updateButtons();
			}
		});
		weekComboBox.setBounds(368, 123, 101, 20);
		frame.getContentPane().add(weekComboBox);

		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(192, 102, 46, 14);
		frame.getContentPane().add(lblYear);

		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(368, 102, 46, 14);
		frame.getContentPane().add(lblWeek);

		btnAddYears = new JButton("Add  Years");
		btnAddYears.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedEmployee != null) {
					openAddYearDialog();
				}
			}
		});
		btnAddYears.setBounds(192, 154, 101, 23);
		frame.getContentPane().add(btnAddYears);

		btnAddWeeks = new JButton("Add Weeks");
		btnAddWeeks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedYear != null) {				
					openAddWeekDialog();
				}
			}
		});
		btnAddWeeks.setBounds(368, 154, 101, 23);
		frame.getContentPane().add(btnAddWeeks);

		addHoursWorkedTextField = new JTextField();
		addHoursWorkedTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					addHoursWorked();
				}
			}
		});
		addHoursWorkedTextField.setBounds(597, 308, 40, 20);
		frame.getContentPane().add(addHoursWorkedTextField);
		addHoursWorkedTextField.setColumns(10);

		addHoursWorkedButton = new JButton("Add Hours");
		addHoursWorkedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHoursWorked();
			}
		});
		addHoursWorkedButton.setBounds(647, 307, 108, 23);
		frame.getContentPane().add(addHoursWorkedButton);

		addSickHoursUsedTextFeild = new JTextField();
		addSickHoursUsedTextFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					addSickHoursUsed();
				}
			}
		});
		addSickHoursUsedTextFeild.setBounds(597, 333, 40, 20);
		frame.getContentPane().add(addSickHoursUsedTextFeild);
		addSickHoursUsedTextFeild.setColumns(10);

		addSickHoursUsedButton = new JButton("Add Hours");
		addSickHoursUsedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSickHoursUsed();
			}
		});
		addSickHoursUsedButton.setBounds(647, 332, 108, 23);
		frame.getContentPane().add(addSickHoursUsedButton);
		
		JLabel lblSickHoursUsedInYear = new JLabel("Sick Hours Used In Selected Year:");
		lblSickHoursUsedInYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSickHoursUsedInYear.setBounds(192, 241, 294, 14);
		frame.getContentPane().add(lblSickHoursUsedInYear);
		
		sickHoursUsedInSelectedYear = new JLabel("0");
		sickHoursUsedInSelectedYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sickHoursUsedInSelectedYear.setBounds(530, 241, 170, 14);
		frame.getContentPane().add(sickHoursUsedInSelectedYear);
		
		btnExportToDocument = new JButton("Export To Document");
		btnExportToDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExportSettingsPopup dialog = new ExportSettingsPopup(selectedYear);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnExportToDocument.setBounds(566, 384, 170, 23);
		frame.getContentPane().add(btnExportToDocument);
		
		updateButtons();
	}

	//method called whenever the add employee button is pressed or when the user hits enter within the add employee text box
	private void addEmployeeButtonPressed() {
		if(newEmployeeNameTextField.getText().length() == 0) return;

		listModel.addElement(new Employee(newEmployeeNameTextField.getText())); //make a new employee object and add it to the list
		employeeList.setSelectedIndex(listModel.getSize() - 1);
		newEmployeeNameTextField.setText(""); //makes the text field blank

	}

	private void disclaimerLabelPressed() {
		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File("EULAforIBLG082615.pdf");
				Desktop.getDesktop().open(myFile);
//				Desktop.getDesktop().open(new File(MainWindow.class.getResource("/EULAforIBLG082615.pdf").getFile()));
			} catch (IOException ex) {
				// no application registered for PDFs
			}
		}
	}

	private void editEmployeeButtonPressed() {
		try {
			EditEmployeePopup dialog = new EditEmployeePopup(selectedEmployee, selectedYear, selectedWeek, this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void employeeSelected(int index) {

		if(index == -1) {
			employeeNameLabel.setText("");
			sickHoursUsedLabel.setText(String.valueOf(""));
			hoursWorkedLabel.setText(String.valueOf(""));
			sickHoursEarnedLabel.setText(String.valueOf(""));
			sickHoursRemainingLabel.setText(String.valueOf(""));

			selectedEmployee = null;
			selectedIndex = -1;

			return;
		}

		selectedEmployee = listModel.getElementAt(index);
		selectedIndex = index;

		yearComboBox.removeAllItems();
		for(Year year : selectedEmployee.hours.years) {
			yearComboBox.addItem(year);
		}

		selectedYear = yearComboBox.getItemAt(yearComboBox.getSelectedIndex());

		weekComboBox.removeAllItems();

		if(selectedYear != null) {
			for(Week week : selectedYear.weeks) {
				weekComboBox.addItem(week);
			}
		}

		selectedWeek = weekComboBox.getItemAt(weekComboBox.getSelectedIndex());

		if(selectedYear != null && selectedWeek != null){
			updateWeekSensitiveEmployeeLabels();
		}
		updateNonWeekSensitiveEmployeeLabels();
		
		updateButtons();
	}

	private void updateNonWeekSensitiveEmployeeLabels() {
		if(selectedYear == null) {
			employeeNameLabel.setText("");
			sickHoursEarnedLabel.setText("");
			sickHoursUsedInSelectedYear.setText("");
			sickHoursRemainingLabel.setText("");
			
			return;
		}

		employeeNameLabel.setText(selectedEmployee.name);
		sickHoursEarnedLabel.setText(String.valueOf(Math.round(selectedYear.totalSickHoursEarned()*10)/10.0));
		sickHoursUsedInSelectedYear.setText(String.valueOf(Math.round(selectedYear.totalSickHoursUsed()*10)/10.0));
		sickHoursRemainingLabel.setText(String.valueOf(Math.round(selectedYear.totalSickHoursLeft()*10)/10.0));		
	}

	private void updateWeekSensitiveEmployeeLabels() {
		if(selectedWeek == null) {
			sickHoursUsedLabel.setText("");
			hoursWorkedLabel.setText("");
			
			return;
		}
		sickHoursUsedLabel.setText(String.valueOf(Math.round(selectedWeek.sickHoursUsed*10)/10.0));
		hoursWorkedLabel.setText(String.valueOf(Math.round(selectedWeek.hoursWorked*10)/10.0));
	}

	private void updateAllEmployeeLabels() {
		updateWeekSensitiveEmployeeLabels();
		updateNonWeekSensitiveEmployeeLabels();
	}

	private void openAddYearDialog() {
		AddYear dialog = new AddYear(selectedEmployee, this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private void openAddWeekDialog() {
		AddWeek dialog = new AddWeek(selectedYear, this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private void addHoursWorked() {
		if(selectedWeek == null || addHoursWorkedTextField.getText() == "") return;

		selectedWeek.addWorkHours(Double.parseDouble(addHoursWorkedTextField.getText()));
		addHoursWorkedTextField.setText("");

		updateAllEmployeeLabels();
	}

	private void addSickHoursUsed() {
		if(selectedWeek == null || addSickHoursUsedTextFeild.getText() == "") return;

		selectedWeek.useSickHours(Double.parseDouble(addSickHoursUsedTextFeild.getText()));
		addSickHoursUsedTextFeild.setText("");

		updateAllEmployeeLabels();
	}

	private void saveEmployees() {
		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("Sick Hours Data.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(listModel);
			out.close();
			fileOut.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private DefaultListModel<Employee> loadEmployees() {
		DefaultListModel<Employee> e = new DefaultListModel<Employee>();
		try
		{
			FileInputStream fileIn = new FileInputStream("Sick Hours Data.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (DefaultListModel<Employee>) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();
			return e;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Employee class not found");
			c.printStackTrace();
			return e;
		}
		
		return e;
	}
	
	private void updateButtons() {
		if(selectedEmployee == null) {
			btnAddYears.setEnabled(false);
			btnAddWeeks.setEnabled(false);
			editEmployeeButton.setEnabled(false);
		} else {
			btnAddYears.setEnabled(true);
			btnAddWeeks.setEnabled(true);
			editEmployeeButton.setEnabled(true);
		}
		
		if(selectedWeek == null) {
			addHoursWorkedButton.setEnabled(false);
			addSickHoursUsedButton.setEnabled(false);
		} else {
			addHoursWorkedButton.setEnabled(true);
			addSickHoursUsedButton.setEnabled(true);
		}
	}


}
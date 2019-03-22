package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class EditEmployeePopup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField employeeNameTextField;
	private JLabel lblEmployeeName;
	private JTextField hoursWorkedTextField;
	private JTextField sickHoursUsedTextField;

	/**
	 * Create the dialog.
	 */
	public EditEmployeePopup(Employee employee, Year year, Week week, MainWindow mainWindow) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		this.setTitle("Information for " + employee.name);
		contentPanel.setToolTipText("");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblEmployeeName = new JLabel("Employee Name:");
			lblEmployeeName.setBounds(20, 13, 140, 20);
			lblEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblEmployeeName);
		}
		{
			employeeNameTextField = new JTextField();
			employeeNameTextField.setBounds(187, 10, 166, 20);
			contentPanel.add(employeeNameTextField);
			employeeNameTextField.setColumns(20);

			employeeNameTextField.setText(employee.name);
		}
		{
			JLabel lblHoursWorked = new JLabel("Hours Worked:");
			lblHoursWorked.setBounds(40, 126, 130, 20);
			lblHoursWorked.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblHoursWorked);
		}
		{
			JLabel lblSickHoursUsed = new JLabel("Sick Hours Used:");
			lblSickHoursUsed.setBounds(40, 157, 145, 20);
			lblSickHoursUsed.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblSickHoursUsed);
		}

		if(year != null) {
		hoursWorkedTextField = new JTextField();
		hoursWorkedTextField.setBounds(187, 129, 86, 20);
		contentPanel.add(hoursWorkedTextField);
		hoursWorkedTextField.setColumns(10);
		hoursWorkedTextField.setText(String.valueOf(week.hoursWorked));


		sickHoursUsedTextField = new JTextField();
		sickHoursUsedTextField.setBounds(187, 160, 86, 20);
		contentPanel.add(sickHoursUsedTextField);
		sickHoursUsedTextField.setColumns(10);
		sickHoursUsedTextField.setText(String.valueOf(week.sickHoursUsed));


			JLabel lblNewLabel = new JLabel("For The Week Of " + week.toString() + " In The Year  " + year.toString());
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setBounds(40, 70, 394, 14);
			contentPanel.add(lblNewLabel);
		}

		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblEmployeeName, employeeNameTextField}));
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if(employeeNameTextField.getText().length() != 0) {
							employee.name = employeeNameTextField.getText();
							week.sickHoursUsed = Double.parseDouble(sickHoursUsedTextField.getText());
							week.hoursWorked = Double.parseDouble(hoursWorkedTextField.getText());

							mainWindow.returnFromEditEmployee(employee);

							dispose();
						}
					}
				});
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
				{


					JButton deleteButton = new JButton("Delete Employee");
					buttonPane.add(deleteButton);
					deleteButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();

							EmployeeDeletionWarning dialog = new EmployeeDeletionWarning(mainWindow, employee);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);

						}
					});
					deleteButton.setActionCommand("Cancel");
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(163);
					buttonPane.add(horizontalStrut);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						mainWindow.returnFromEditEmployee(null);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}


		}
	}
}
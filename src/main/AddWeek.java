package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddWeek extends JDialog {
	public AddWeek() {
		setResizable(false);
	}
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public AddWeek(Year year, MainWindow mainWindow) {
		setBounds(100, 100, 323, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewWeek = new JLabel("New Week");
			lblNewWeek.setBounds(121, 11, 58, 14);
			lblNewWeek.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblNewWeek);
		}
		{
			JLabel lblMonth = new JLabel("Month:");
			lblMonth.setBounds(10, 41, 49, 14);
			contentPanel.add(lblMonth);
		}
		{
			JLabel lblDay = new JLabel("Day:");
			lblDay.setBounds(157, 41, 38, 14);
			contentPanel.add(lblDay);
		}
		
		String[] dayArray = new String[Month.APRIL.length(java.time.Year.isLeap(year.year))];
		
		for(int x = 1; x <= dayArray.length; x++) {
			dayArray[x-1] = String.valueOf(x);
		}
		
		JComboBox<String> dayComboBox = new JComboBox<String>(dayArray);
		dayComboBox.setBounds(188, 38, 58, 20);
		contentPanel.add(dayComboBox);
		
		JComboBox<Month> monthComboBox = new JComboBox<Month>(Month.values());
		monthComboBox.setBounds(51, 38, 96, 20);
		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] dayArray = new String[monthComboBox.getItemAt(monthComboBox.getSelectedIndex()).length(java.time.Year.isLeap(year.year))];
				
				for(int x = 1; x <= dayArray.length; x++) {
					dayArray[x-1] = String.valueOf(x);
				}
				
				dayComboBox.removeAllItems();
				
				for(String day : dayArray) {					
					dayComboBox.addItem(day);
				}
			}
		});
		contentPanel.add(monthComboBox);
		

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Week newWeek = new Week(0, 0, monthComboBox.getItemAt(monthComboBox.getSelectedIndex()).getValue(), Integer.parseInt(dayComboBox.getItemAt(dayComboBox.getSelectedIndex())));
						mainWindow.addWeek(newWeek);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}

package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

public class ExportSettingsPopup extends JDialog {

	private static final long serialVersionUID = 1L;


	/**
	 * Create the dialog.
	 * @param selectedYear 
	 */
	public ExportSettingsPopup(Year selectedYear) {
		setResizable(false);
		setBounds(100, 100, 382, 138);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 76, 339, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JComboBox<Year> comboBox = new JComboBox<Year>();
		comboBox.setBounds(27, 11, 111, 20);
		getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("Export Single Employee Information");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(148, 10, 218, 23);
		getContentPane().add(btnNewButton);
		
		JButton allInfo = new JButton("Export All Employee's Information");
		allInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		allInfo.setBounds(148, 44, 218, 23);
		getContentPane().add(allInfo);
	}
}

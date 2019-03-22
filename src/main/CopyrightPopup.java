package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class CopyrightPopup extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public CopyrightPopup() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JLabel lblNewLabel = new JLabel("Disclaimer");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
				buttonPane.add(lblNewLabel);
			}
		}
		{
			JButton okButton = new JButton("OK");
			getContentPane().add(okButton, BorderLayout.SOUTH);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				JTextPane txtpnDisclaimer = new JTextPane();
				txtpnDisclaimer.setEditable(false);
				txtpnDisclaimer.setFont(new Font("Tahoma", Font.PLAIN, 11));
				txtpnDisclaimer.setText("First Line \r\n jn");
				scrollPane.setViewportView(txtpnDisclaimer);
			}
		}
	}

}
package studentflashcard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * @author Vikasini
 * 
 */

public class DisplayContact extends JPanel {

	private JButton toButton, ccButton, okButton, cancelButton;
	JFrame newFrame;
	private JTextField toTextField, ccTextField;
	StringBuffer toBuffer = new StringBuffer();
	StringBuffer ccBuffer = new StringBuffer();
	String selectedData = null;

	public DisplayContact() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * displays the contact list
	 */
	private void init() throws IOException, ClassNotFoundException {

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		Vector v = null;
		newFrame = new JFrame("All Contacts In The Address Book");
		newFrame.setSize(600, 450);

		FileInputStream fis;
		try {
			fis = new FileInputStream("filename.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			v = (Vector) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Contact con = new Contact();

		String columnNames[] = { "First Name", "Last Name", "E Mail Address",
				"Address", "Phone No." };

		Object data[][] = new Object[v.size()][8];
		int k = 0;

		for (int j = 0; j < v.size(); j++) {

			con = (Contact) v.elementAt(k);

			data[j][0] = con.getFName();
			data[j][1] = con.getLName();
			data[j][2] = con.getEMail();
			data[j][3] = con.getAddress();
			data[j][4] = con.getPhoneNo();

			k++;

		}
		k = 0;

		final JTable abtable = new JTable(data, columnNames);

		toButton = new JButton();
		ccButton = new JButton();
		okButton = new JButton();
		cancelButton = new JButton();
		toButton.setText("To");
		ccButton.setText("CC");
		okButton.setText("OK");
		cancelButton.setText("Cancel");

		toButton.setBounds(20, 250, 60, 20);
		ccButton.setBounds(20, 300, 60, 20);
		okButton.setBounds(150, 350, 100, 30);
		cancelButton.setBounds(250, 350, 100, 30);

		toTextField = new javax.swing.JTextField(20);
		ccTextField = new javax.swing.JTextField(20);

		toTextField.setBounds(100, 250, 200, 30);

		ccTextField.setBounds(100, 300, 200, 30);

		toButton.addActionListener(new java.awt.event.ActionListener()

		{

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)

			{
				if (selectedData != "" || selectedData != null) {
					toBuffer = toBuffer.append(selectedData + ",");
					toTextField.setText(toBuffer.toString());

				}
			}

		});
		ccButton.addActionListener(new java.awt.event.ActionListener()

		{

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)

			{
				if (selectedData != "" || selectedData != null) {
					ccBuffer = ccBuffer.append(selectedData + ",");
					ccTextField.setText(ccBuffer.toString());

				}
			}

		});

		newFrame.add(toButton);
		newFrame.add(ccButton);
		newFrame.add(okButton);
		newFrame.add(cancelButton);
		newFrame.add(toTextField);
		newFrame.add(ccTextField);

		abtable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				int row = abtable.getSelectedRow();
				selectedData = (String) abtable.getValueAt(row, 2);

				System.out.println("Selected: " + selectedData);
			}

		});
		cancelButton.addActionListener(new java.awt.event.ActionListener()

		{

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				toBuffer = null;
				ccBuffer = null;
				newFrame.dispose();

			}

		});

		okButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				emailIdList(toBuffer, ccBuffer, true);
				newFrame.dispose();
			}

		});

		JScrollPane scrollPane = new JScrollPane(abtable);
		abtable.setPreferredScrollableViewportSize(new Dimension(500, 370));
		// add(scrollPane);
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts Currently In The Address Book");
		pane.add(label);

		newFrame.getContentPane().add(pane, BorderLayout.SOUTH);
		newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		newFrame.show();

	}

	/*
	 * append the value to the TO field
	 */
	public void emailIdList(StringBuffer tobuffer, StringBuffer ccbuffer,
			boolean b) {
		EmailValues value = new EmailValues(tobuffer, b);
		String emailId = null;
		value.setBuffer(tobuffer);
		if (MailSender.toTextFieldData.getText().trim().equals("")
				&& toTextField.getText().equals("") == false) {
			MailSender.toTextFieldData.setText(tobuffer.toString());
			if (MailSender.ccTextField.getText().equals("")
					&& ccTextField.getText().equals("") == false)

				MailSender.ccTextField.setText(ccbuffer.toString());
		} else if (MailSender.ccTextField.getText().equals("")
				&& ccTextField.getText().equals("") == false) {

			MailSender.ccTextField.setText(ccbuffer.toString());
		} else if (toTextField.getText().equals("") == false
				&& MailSender.toTextFieldData.getText().equals("") == false) {
			emailId = MailSender.toTextFieldData.getText();
			MailSender.toTextFieldData.setText(emailId + toTextField.getText());
			if (ccTextField.getText().equals("") == false
					&& MailSender.ccTextField.getText().equals("") == false) {
				String ccemailId = MailSender.ccTextField.getText();
				MailSender.ccTextField.setText(ccemailId + ","
						+ ccTextField.getText());
			}

		} else if (ccTextField.getText().equals("") == false
				&& MailSender.ccTextField.getText().equals("") == false) {
			String ccemailId = MailSender.ccTextField.getText();
			MailSender.ccTextField.setText(ccemailId + ","
					+ ccTextField.getText());
		}

	}

}
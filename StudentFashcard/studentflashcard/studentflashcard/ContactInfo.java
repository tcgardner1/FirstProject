package studentflashcard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Vikasini
 * 
 */

class ContactInfo implements ActionListener {

	JPanel topPanel, bottomPanel;
	JScrollPane scrollPane;
	JFrame frame;

	JMenuBar menubar = new JMenuBar();;
	JMenu menu = new JMenu();
	JMenuItem menuItem;

	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;

	Image img = kit.getImage("images/icon.JPG");

	ContactInfo(Controller controller, GUI gui) {

		frame = new JFrame("Address Book");
		frame.setSize(680, 200);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(screenWidth / 4, screenHeight / 4);
		frame.setIconImage(img);
		addWidgets();
		frame.show();

	}

	/* adding buttons to the panel and adding menu items */
	public void addWidgets() {
		menubar.add(menu);

		menu = new JMenu("Options");
		menuItem = new JMenuItem("Add New Contact");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Delete Contact");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Search Contacts");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("View All Contacts");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menubar.add(menu);

		menubar.add(menu);

		frame.setJMenuBar(menubar);

		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// Add Buttons To Bottom Panel
		JButton AddNew = new JButton("Add New Contact");
		JButton DeleteContact = new JButton("Delete Contact");
		JButton SearchContacts = new JButton("Search Contacts");
		JButton ViewContactList = new JButton("View All Contacts");

		JButton close = new JButton("Close");

		JLabel label = new JLabel(
				"<HTML><FONT FACE = ARIAL SIZE = 3><B>Use the options below or in the menu to manage contacts");

		// Add Action Listeners
		AddNew.addActionListener(this);
		DeleteContact.addActionListener(this);
		SearchContacts.addActionListener(this);
		ViewContactList.addActionListener(this);
		close.addActionListener(this);

		topPanel.add(label);

		bottomPanel.add(AddNew);
		bottomPanel.add(DeleteContact);
		bottomPanel.add(SearchContacts);
		bottomPanel.add(ViewContactList);
		bottomPanel.add(close);

		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		frame.setResizable(false);

	}

	OperationHandler oh = new OperationHandler();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/*
	 * action performed on click
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == "Add New Contact") {
			oh.AddNew();

		} else if (ae.getActionCommand() == "Close") {
			this.frame.dispose();
		}

		else if (ae.getActionCommand() == "Search Contacts") {
			oh.SearchContacts();

		}

		else if (ae.getActionCommand() == "Delete Contact") {
			oh.DeleteContact();

		}

		else if (ae.getActionCommand() == "View All Contacts") {

			oh.ViewAllContacts();

		}
	}
}

class Contact implements Serializable {
	private String FName;
	private String LName;
	private String EMail;
	private String Address;
	private String PhoneNo;

	public void setDetails(String fname, String lname, String email,
			String address, String phone) {
		FName = fname;
		LName = lname;
		EMail = email;
		Address = address;
		PhoneNo = phone;
	}

	public String getFName() {
		return FName;
	}

	public String getLName() {
		return LName;
	}


	public String getEMail() {
		return EMail;
	}

	public String getAddress() {
		return Address;
	}

	public String getPhoneNo() {
		return PhoneNo;
	}


}

class OperationHandler implements ListSelectionListener, ActionListener,
		Runnable {

	JFrame newFrame;
	JTextField txtFirstName;
	JTextField txtLastName;
	JTextField txtEMail;
	JTextField txtAddress;
	JTextField txtPhoneNo;


	Vector v = new Vector(10, 3);
	int i = 0, k = 0;

	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;

	Image img = kit.getImage("images/icon.JPG");

	FileInputStream fileInputStream;
	ObjectInputStream objectOutputStream;

	JList list;
	DefaultListModel defaultlistModel;
	ListSelectionModel listSelectionModel;

	JRadioButton byfname, bylname;

	Thread t;

	JTable searchTable;

	JTextField txtSearch;

	String columnNames[] = { "First Name", "Last Name", "E Mail Address",
			"Address", "Phone No." };

	Object data[][] = new Object[5][8];

	OperationHandler() {

		try {
			fileInputStream = new FileInputStream("filename.txt");
			objectOutputStream = new ObjectInputStream(fileInputStream);
			v = (Vector) objectOutputStream.readObject();
			objectOutputStream.close();
		}

		catch (Exception e) {

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			File file = new File("filename.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(v);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(newFrame,
					"Error OpeningData File: Could Not Save Contents.",
					"Error Opening Data File", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/*
	 * add frame and fields
	 */
	public void AddNew() {
		newFrame = new JFrame("Add New");
		newFrame.setSize(220, 250);
		newFrame.setResizable(false);
		newFrame.setIconImage(img);

		JLabel lblFirstName = new JLabel("First Name: ");
		JLabel lblLastName = new JLabel("Last Name: ");
		JLabel lblEMail = new JLabel("EMail: ");
		JLabel lblAddress = new JLabel("Address: ");
		JLabel lblPhoneNo = new JLabel("Phone No: ");


		txtFirstName = new JTextField(10);
		txtLastName = new JTextField(10);
		txtEMail = new JTextField(10);
		txtAddress = new JTextField(10);
		txtPhoneNo = new JTextField(10);

		JButton BttnAdd = new JButton("Add New");


		BttnAdd.addActionListener(this);

		JPanel centerPane = new JPanel();
		JPanel bottomPane = new JPanel();

		centerPane.add(lblFirstName);
		centerPane.add(txtFirstName);
		centerPane.add(lblLastName);
		centerPane.add(txtLastName);
		centerPane.add(lblEMail);
		centerPane.add(txtEMail);
		centerPane.add(lblAddress);
		centerPane.add(txtAddress);
		centerPane.add(lblPhoneNo);
		centerPane.add(txtPhoneNo);
		bottomPane.add(BttnAdd);

		centerPane.setLayout(new GridLayout(0, 2));

		newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);

		newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);
		newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		newFrame.show();

	}

	/*
	 * search frame and fields
	 */
	public void SearchContacts() {
		newFrame = new JFrame("Search Contacts");
		newFrame.setSize(700, 220);
		newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		newFrame.setIconImage(img);
		newFrame.setResizable(false);

		JPanel topPane = new JPanel();
		JLabel label1 = new JLabel(
"");
		topPane.add(label1);

		JPanel centerPane = new JPanel();
		JLabel label2 = new JLabel("Search :");
		txtSearch = new JTextField(20);
		JButton bttnSearch = new JButton("Search");
		bttnSearch.addActionListener(this);
		JButton bttnCancel = new JButton("Cancel");
		bttnCancel.addActionListener(this);
		centerPane.add(label2);
		centerPane.add(txtSearch);
		centerPane.add(bttnSearch);
		centerPane.add(bttnCancel);

		searchTable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(searchTable);
		searchTable.setPreferredScrollableViewportSize(new Dimension(500, 90));

		newFrame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

		newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);
		newFrame.show();
	}

	/*
	 * delete frame and fields
	 */
	public void DeleteContact() {
		String fname, lname;
		newFrame = new JFrame("Delete Contact");
		newFrame.setSize(300, 300);
		newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		newFrame.setIconImage(img);

		JPanel centerPane = new JPanel();
		defaultlistModel = new DefaultListModel();

		Contact contact = new Contact();

		for (int l = 0; l < v.size(); l++) {
			contact = (Contact) v.elementAt(l);

			fname = contact.getFName();
			lname = contact.getLName();
			defaultlistModel.addElement(fname + " " + lname);

		}

		list = new JList(defaultlistModel);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(this);

		JScrollPane listScrollPane = new JScrollPane(list);

		JPanel topPane = new JPanel();
		JLabel label = new JLabel("Current Contacts:");
		topPane.add(label);

		JPanel bottomPane = new JPanel();

		JButton bttnDelete = new JButton("Delete Selected");
		bottomPane.add(bttnDelete);
		bttnDelete.addActionListener(this);

		JButton bttnCancel = new JButton("Cancel");
		bottomPane.add(bttnCancel);
		bttnCancel.addActionListener(this);

		newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		newFrame.getContentPane().add(listScrollPane, BorderLayout.CENTER);
		newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

		newFrame.show();

	}

	/*
	 * view contacts frame
	 */
	public void ViewAllContacts() {

		newFrame = new JFrame("All Contacts In The Address Book");
		newFrame.setSize(600, 300);
		newFrame.setIconImage(img);

		Contact con = new Contact();

		String columnNames[] = { "First Name", "Last Name", "E Mail Address",
				"Address", "Phone No." };

		Object data[][] = new Object[v.size()][8];

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

		JTable abtable = new JTable(data, columnNames);// table format of
														// viewing contacts
		JScrollPane scrollPane = new JScrollPane(abtable);
		abtable.setPreferredScrollableViewportSize(new Dimension(500, 370));

		JPanel pane = new JPanel();
		JLabel label = new JLabel("Contacts Currently In The Address Book");
		pane.add(label);

		newFrame.getContentPane().add(pane, BorderLayout.SOUTH);
		newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		newFrame.show();

	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getActionCommand() == "Add New") {

			if (txtFirstName.getText().equals("")
					&& txtLastName.getText().equals("")
					&& txtEMail.getText().equals("")
					&& txtAddress.getText().equals("")
					&& txtPhoneNo.getText().equals("")) {

				JOptionPane
						.showMessageDialog(
								newFrame,
								"Entries Empty Fill in the required entries to save Contact.",
								"Entries Empty",
								JOptionPane.INFORMATION_MESSAGE);

			}

			else {
				Contact contact = new Contact();

				contact.setDetails(txtFirstName.getText(), txtLastName
						.getText(), txtEMail.getText(), txtAddress.getText(),
						txtPhoneNo.getText());
				v.addElement(contact);

				saveVector();
				newFrame.setVisible(false);

			}

		} else if (ae.getActionCommand() == "Save Added") {




		} else if (ae.getActionCommand() == "Ok") {
			newFrame.setVisible(false);

		} else if (ae.getActionCommand() == "Delete Selected") {

			int index;
			try {

				index = list.getSelectedIndex();

				if (index == -1) {

					JOptionPane.showMessageDialog(newFrame,
							"Select first a contact to delete it",
							"Select contact First",
							JOptionPane.INFORMATION_MESSAGE);
				}

				else {

					int n = JOptionPane
							.showConfirmDialog(
									newFrame,
							"Are you sure you want to delete the Contact?",
							"Sure ?", JOptionPane.YES_NO_OPTION);

					if (n == JOptionPane.YES_OPTION) {
						defaultlistModel.remove(index);
						v.removeElementAt(index);
						saveVector();
						newFrame.show();

					} else if (n == JOptionPane.NO_OPTION) {

					}

				}

			} catch (Exception e) {

			}

		} else if (ae.getActionCommand() == "Cancel") {

			newFrame.setVisible(false);
		} else if (ae.getActionCommand() == "Search") {
			String SearchStr;
			SearchStr = txtSearch.getText();
			boolean flag = false;
			Contact con = new Contact();
			int c = 0;

			for (int t = 0; t < 5; t++) {
				data[t][0] = "";
				data[t][1] = "";
				data[t][2] = "";
				data[t][3] = "";
				data[t][4] = "";
			}

			for (int t = 0; t < v.size(); t++) {

				con = (Contact) v.elementAt(t);

				if (SearchStr.equalsIgnoreCase(con.getFName())
						|| SearchStr.equalsIgnoreCase(con.getLName())
						|| SearchStr.equalsIgnoreCase(con.getFName() + " "
								+ con.getLName())) {
					flag = true;

					data[c][0] = con.getFName();
					data[c][1] = con.getLName();
					data[c][2] = con.getEMail();
					data[c][3] = con.getAddress();
					data[c][4] = con.getPhoneNo();
					searchTable = new JTable(data, columnNames);
					newFrame.setSize(700, 221);
					newFrame.setSize(700, 220);

					if (c < 5)
						c++;
				}

			}

			if (flag) {
				JOptionPane.showMessageDialog(newFrame, "Contact Found",
						"Search Result", JOptionPane.INFORMATION_MESSAGE);
			}

			else {
				JOptionPane.showMessageDialog(newFrame,
						"No such Contact Found",
						"Search Result",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}

	}

	public void saveVector() {
		t = new Thread(this, "");
		t.start();

	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {

	}

}

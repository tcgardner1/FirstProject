package studentflashcard;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * @author Vikasini
 * 
 */

public class MailSender extends JFileChooser {
	private JTextField attachmentTextField;

	private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6,
			jlabel7, jlabel8;

	private JScrollPane jScrollPane1;

	private JTextArea messageTextArea;
	JFrame mailFrame;

	private JButton sendButton, cancelButton, browseButton, addressBook;

	private JTextField subjectTextField, fromTextField,
			passwordField;

	public static JTextField toTextFieldData, ccTextField;

	String toAddress = null, ccAddress = null, message = null,

	receipientsList[] = null, attachments[] = null, receipients, subject;

	String fromAddress = null;

	String authenticationPassword = null;// Place your password here

	public MailSender(Controller controller, Project project)

	{

		initComponents(controller, project);

	}

	private void initComponents(Controller controller, Project project)

	{

		jLabel1 = new javax.swing.JLabel();

		jLabel2 = new javax.swing.JLabel("To");

		addressBook = new javax.swing.JButton("Address Book");

		toTextFieldData = new javax.swing.JTextField(20);

		jlabel7 = new javax.swing.JLabel("Email id");

		fromTextField = new javax.swing.JTextField(20);

		jlabel8 = new javax.swing.JLabel("Password");

		passwordField = new JPasswordField(20);

		jLabel3 = new javax.swing.JLabel("CC");
		ccTextField = new javax.swing.JTextField(20);

		jLabel4 = new javax.swing.JLabel("Subject");

		subjectTextField = new javax.swing.JTextField(20);

		jLabel5 = new javax.swing.JLabel("Attachemnet");

		attachmentTextField = new javax.swing.JTextField(20);

		browseButton = new javax.swing.JButton("Browse");

		jLabel6 = new javax.swing.JLabel("Message");

		jScrollPane1 = new javax.swing.JScrollPane();

		messageTextArea = new javax.swing.JTextArea(20, 10);

		sendButton = new javax.swing.JButton();

		cancelButton = new javax.swing.JButton();

		jLabel1.setFont(new java.awt.Font("AlMateen-Bold", 3, 14));

		jLabel1.setForeground(new java.awt.Color(1, 203, 221));
		jLabel1.setText("Mail Sender");

		jScrollPane1.setViewportView(messageTextArea);

		sendButton.setText("Send");

		Document document = toTextFieldData.getDocument();
		document.addDocumentListener(new ControllerButton(sendButton));

		java.io.File projectFolder = project.getFolder();
		fileZipforAttachment(projectFolder);
		File zip = new File(projectFolder.getAbsolutePath() + "/"
				+ project.getName() + "." + Zipper.EXTENSION);

		attachmentTextField.setText(zip.getAbsolutePath().toString());

		sendButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				sendButtonActionPerformed(evt);

			}

		});

		cancelButton.setText("Cancel");

		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				cancelButtonActionPerformed(evt);

			}

		});

		addressBook.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)

			{

				adressButtonActionPerformed(evt);

			}

		});


		browseButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)

			{

				browseButtonActionPerformed(evt);

			}

		});

		jLabel1.setBounds(100, 50, 200, 50);

		jlabel7.setBounds(50, 100, 100, 50);

		fromTextField.setBounds(150, 100, 300, 40);

		jlabel8.setBounds(50, 150, 100, 50);

		passwordField.setBounds(150, 150, 300, 40);

		jLabel2.setBounds(50, 200, 100, 50);

		toTextFieldData.setBounds(150, 200, 300, 40);

		addressBook.setBounds(450, 200, 140, 20);

		jLabel3.setBounds(50, 250, 100, 50);

		ccTextField.setBounds(150, 250, 300, 40);

		jLabel4.setBounds(50, 300, 100, 50);

		subjectTextField.setBounds(150, 300, 300, 40);

		jLabel5.setBounds(50, 350, 100, 50);

		attachmentTextField.setBounds(150, 350, 200, 40);

		browseButton.setBounds(375, 350, 100, 20);

		jLabel6.setBounds(50, 400, 100, 50);

		messageTextArea.setBounds(150, 400, 400, 40);

		sendButton.setBounds(100, 450, 75, 20);

		cancelButton.setBounds(200, 450, 75, 20);

		mailFrame = new JFrame("Mail Sender");

		mailFrame.add(jLabel1);

		mailFrame.add(jlabel7);

		mailFrame.add(fromTextField);

		mailFrame.add(jlabel8);

		mailFrame.add(passwordField);

		mailFrame.add(jLabel2);

		mailFrame.add(toTextFieldData);

		mailFrame.add(jLabel3);

		mailFrame.add(ccTextField);

		mailFrame.add(jLabel4);

		mailFrame.add(subjectTextField);

		mailFrame.add(jLabel5);

		mailFrame.add(attachmentTextField);

		mailFrame.add(browseButton);

		mailFrame.add(addressBook);

		mailFrame.add(jLabel6);

		mailFrame.add(messageTextArea);

		mailFrame.add(sendButton);

		mailFrame.add(cancelButton);

		mailFrame.setLayout(null);
		mailFrame.setVisible(true);

		mailFrame.setSize(600, 600);

		mailFrame.setResizable(false);

		mailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private File zipFile(File projectFolder) {
		try {
			BufferedInputStream origin = null;

			// create a .zip file inside the zipTo folder
			String projectName = projectFolder.getName();
			File zip = new File(projectFolder.getAbsolutePath() + "/"
					+ projectName + "." + Zipper.EXTENSION);

			// and have the output stream write to the zip file
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zip)));
			byte[] data = new byte[Zipper.BUFFER_SIZE];

			// get a list of files from given folder
			String[] files = projectFolder.list();

			for (int i = 0; i < files.length; i++) {
				File file = new File(files[i]);
				String absolutePath = projectFolder.getAbsolutePath() + "/"
						+ file.getName();
				File newLocation = new File(absolutePath);
				origin = new BufferedInputStream(new FileInputStream(
						newLocation), Zipper.BUFFER_SIZE);
				ZipEntry entry = new ZipEntry(files[i]);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, Zipper.BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
			return zip;
		} catch (Exception e) {
			System.err.println("Error zipping project!!!! Details: ");
			e.printStackTrace();
			return null;
		}
	}

	private void fileZipforAttachment(final File projectFolder) {

		java.io.File zipped = zipFile(projectFolder);

	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)

	{
		mailFrame.dispose();

	}

	private void adressButtonActionPerformed(java.awt.event.ActionEvent evt)

	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DisplayContact onClick = new DisplayContact();

	}

	private void browseButtonActionPerformed(java.awt.event.ActionEvent evt)

	{

		JFileChooser selectFile = new JFileChooser();

		selectFile.showOpenDialog(this);

		if(selectFile.getSelectedFile()!=null)
		attachmentTextField.setText(selectFile.getSelectedFile().toString());

	}

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt)

	{
		fromAddress = fromTextField.getText().trim();

		authenticationPassword = passwordField.getText().trim();

		toAddress = toTextFieldData.getText().trim();

		ccAddress = ccTextField.getText().trim();

		subject = subjectTextField.getText().trim();

		message = messageTextArea.getText().trim();

		String attachment = attachmentTextField.getText() + " ";

		receipients = toAddress + "," + ccAddress;

		receipientsList = receipients.split(",");

		SendingMail mailUsingAuthentication =

		new SendingMail();

		try {
			mailUsingAuthentication.postMail(receipientsList,

			subject, message, fromAddress, authenticationPassword, attachment);

		} catch (MessagingException messagingException) {

			messagingException.printStackTrace();

		}
		mailFrame.dispose();

	}

	void login(String userName, String password)

	{

		fromAddress = userName;

		authenticationPassword = password;

		System.out.println("User name : " + fromAddress);
	}

	class ControllerButton implements DocumentListener {
		JButton button;

		ControllerButton(JButton button) {
			this.button = button;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			enable(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			enable(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			enable(e);
		}

		public void enable(DocumentEvent e) {
			button.setEnabled(e.getDocument().getLength() > 0);
		}
	}

}

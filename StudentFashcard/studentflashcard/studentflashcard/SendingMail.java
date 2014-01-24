package studentflashcard;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Vikasini
 * 
 */

public class SendingMail {

	private String HOST_NAME = "smtp.gmail.com";

	String messageBody;

	public void postMail(String recipients[], String subject, String message,

	String from, String emailPassword, String files) throws MessagingException {

		String server = getServerAddress(from);
		boolean debug = false;


		// Set the host smtp address

		Properties props = new Properties();

		if (server != null
				&& (server.equals("@gmail") || server.equals("@students"))) {
			props.put("mail.transport.protocol", "smtp");

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", HOST_NAME);
			props.setProperty("mail.smtp.port", "587");

			props.put("mail.smtp.auth", "true");
		} else if (server != null && server.equals("@yahoo")) {
			props.put("mail.transport.protocol", "smtp");

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.mail.yahoo.com");
			props.setProperty("mail.smtp.port", "465");

			props.put("mail.smtp.auth", "true");
		}

		Authenticator authenticator = new SMTPAuthenticator(from, emailPassword);
		Session session = null;
		session = Session.getInstance(props, authenticator);

		session.setDebug(debug);

		// create a message

		Message msg = new MimeMessage(session);

		// set the from and to address

		InternetAddress addressFrom = new InternetAddress(from);

		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];

		for (int i = 0; i < recipients.length; i++) {

			addressTo[i] = new InternetAddress(recipients[i]);

		}

		msg.setRecipients(Message.RecipientType.TO, addressTo);

		msg.setSubject(subject);

		msg.setContent(message, "text/plain");

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("Here's the file");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(files.trim());
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(files.trim());
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		try {
			Transport.send(msg);
		} catch (SendFailedException sfe) {
			msg.setRecipients(Message.RecipientType.TO, sfe
					.getValidUnsentAddresses());
			Transport.send(msg);

		}

		System.out.println("Sucessfully Sent mail to All Users");

	}

	/**
	 * @param from
	 */
	private String getServerAddress(String from) {

		int i = from.indexOf("@");
		int j = from.lastIndexOf(".com");
		if (i != -1 && j != -1) {
		return from.substring(i, j);
		} else {
			j = from.lastIndexOf(".jsums");
			return from.substring(i, j);
		}

	}

	/*
	 * Adding attachments
	 */
	protected void addAtachments(String[] attachments, Multipart multipart)

	throws MessagingException, AddressException {

		for (int i = 0; i <= attachments.length - 1; i++) {

			String filename = attachments[i];

			MimeBodyPart attachmentBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(filename);

			attachmentBodyPart.setDataHandler(new DataHandler(source));

			attachmentBodyPart.setFileName(filename);

			multipart.addBodyPart(attachmentBodyPart);

		}

	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		String username;

		String password;

		private SMTPAuthenticator(String authenticationUser,
				String authenticationPassword) {

			username = authenticationUser;

			password = authenticationPassword;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		public PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(username, password);

		}

	}

}

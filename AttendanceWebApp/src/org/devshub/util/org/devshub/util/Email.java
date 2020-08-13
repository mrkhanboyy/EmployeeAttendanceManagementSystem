package org.devshub.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	public static boolean send(String from, String pass, String to, String subject, String message)
			throws AddressException, MessagingException {
		String host = "smtp.gmail.com";
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		// Create a default MimeMessage object.
		MimeMessage msg = new MimeMessage(session);

		// Set From: header field of the header.
		msg.setFrom(new InternetAddress(from));

		// Set To: header field of the header.
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// Set Subject: header field
		msg.setSubject(subject);

		// Now set the actual message
		msg.setText(message);

		// Send message
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
		System.out.println("Mail sent successfully....");
		return true;
	}
}

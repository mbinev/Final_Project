package com.example.validation;

import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static final String SENDER_EMAIL = "dominos.pizza.itt@gmail.com";
	private static final String SENDER_PASS = "dominospizzaitt";

	public static void sendValidationEmail(String receiverEmail,
			String subjectText, String msgText) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

//		Session session = Session.getInstance(props,
//				new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(SENDER_EMAIL,
//								SENDER_PASS);
//					}
//				});
//
//		try {
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress("dominos.pizza.itt@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse(receiverEmail));
//			message.setSubject(subjectText);
//			message.setText(msgText);
//
//			Transport.send(message);
//
//		} catch (AddressException e) {
//			e.printStackTrace();
//			//TODO Handle the exception
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}

		// TODO
		//Make the code expire somehow - CHECK
		//Add field in database - is_verified - CHECK
		// The user clicks the link and request is sent to your site confirmaion
		// servlet; 
		// Confirmation servlet will search for the account associated with the
		// specified confirmation id;
		// Confirmaion servlet set account to "confirmed state" as obvously the
		// user has access to the specified mail box
		//insert user to data base and ask him for password

	}
}

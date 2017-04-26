package com.example.validation;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static final String SENDER_EMAIL = "dominos.pizza.itt@gmail.com";
	private static final String SENDER_PASS = "dominospizzaitt";
	private static final String SUBJECT_TEXT = "Dominos pizza verification";
	private static final String VERIFY_PAGE = "";

	public static String sendValidationEmail(String receiverEmail) throws AddressException, MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASS);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("dominos.pizza.itt@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
		message.setSubject(SUBJECT_TEXT);
		String code = generateCode();
		String msgText = String.format(
				"Hi, this is your verification code - %s, please login with it on the following page: %s", code,
				VERIFY_PAGE);
		message.setText(msgText);

		//Transport.send(message);

		return code;

	}

	private static String generateCode() {
		String letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		int codeLength = 5;
		StringBuilder sb = new StringBuilder(codeLength);
		for (int i = 0; i < codeLength; i++)
			sb.append(letters.charAt(rnd.nextInt(letters.length())));
		return sb.toString();
	}
}

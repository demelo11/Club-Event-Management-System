package application;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void send(String recipient, String title, String body) throws Exception {
		String myEmail="cosc.cmt@gmail.com";
		String password="algomau123";
		
		Properties prop= new Properties();
		
		prop.put("mail.stmp.user", myEmail);
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.host","smtp.gmail.com");
		prop.put("mail.smtp.port","587");
		
		
		
		
		Session session= Session.getInstance(prop,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail,password);
			}
		});
		
		Message m = prepareMessage(session, myEmail,recipient,body,title);
		
		Transport.send(m);
	}

	private static Message prepareMessage(Session session, String myEmail, String recipient,String body, String title) {
		try {
			Message m= new MimeMessage(session);
			m.setFrom(new InternetAddress(myEmail));
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			m.setSubject(title);
			m.setText(body);
			return m;
		}catch(Exception e) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE,null,e);
		}
		return null;
	}
	
}


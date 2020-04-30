import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mailer {
 
	public static ArrayList<String>mailAddressString = new ArrayList<String>();
	public static int mailAddressCounter = 0;
	
    @SuppressWarnings("null")
	public void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws Exception {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        
        
        Address[] multipleAddresses = new Address[mailAddressCounter];
        for(int i=0; i<mailAddressCounter; i++)
        {
        	multipleAddresses[i] = new InternetAddress(mailAddressString.get(i));
        }
        msg.addRecipients(Message.RecipientType.BCC, multipleAddresses);
        
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set message
        DataCollector dataCollector = new DataCollector();
        String content = dataCollector.getMailContent();
        msg.setContent(content,"text/html");
 
        // sends the e-mail
        Transport.send(msg);
        //System.out.println(content);
 
    }
    
    /**
     * collecting recipients mail address from file recipients_mail_address.txt
     */
    public static void collectMailAddresss()
    {
    	String mailString;
    	try {
			FileReader fileReader = new FileReader("src/recipients_mail_address.txt");
			Scanner scanner = new Scanner(fileReader);
			while(scanner.hasNext())
			{
				mailString = scanner.next();
				mailAddressString.add(mailString);
				mailAddressCounter++;
				//System.out.println(mailString);
			}
			//System.out.println(mailAddressCounter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    /**
     * Test the send e-mail method
     *
     */
    public static void main(String[] args) {
        // SMTP server information
    	
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "example@email.com";			// insert sender maill address
        String password = "example";		// insert sender maill account password
 
        // outgoing message information
        String mailTo = "suvashkumar.naogaon@hotmail.com";
        String subject = "COVID-19 Daily Update";
        String message = "";
 
        Mailer mailer = new Mailer();
        collectMailAddresss();
        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        
    }
}
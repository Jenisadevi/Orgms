package com.orgms.util;

	import jakarta.mail.*; import jakarta.mail.internet.*; import java.util.*;
	import java.io.File;
import java.io.IOException;
	public class EmailUtil {
	  private static final String SMTP_HOST="jenisadevi123@gmail.com";
	  private static final String SMTP_USER="smtp-user";
	  private static final String SMTP_PASS="smtp-pass";
	  public static void send(String to,String subject,String body,List<File> attachments) throws MessagingException {
	    Properties props=new Properties();
	    props.put("mail.smtp.host",SMTP_HOST);
	    props.put("mail.smtp.auth","true");
	    Session session=Session.getInstance(props,new Authenticator(){ protected PasswordAuthentication getPasswordAuthentication(){ return new PasswordAuthentication(SMTP_USER,SMTP_PASS); }});
	    Message msg=new MimeMessage(session); msg.setFrom(new InternetAddress("noreply@orgms.com")); msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
	    msg.setSubject(subject);
	    Multipart mp=new MimeMultipart();
	    MimeBodyPart text=new MimeBodyPart(); text.setText(body); mp.addBodyPart(text);
	    if(attachments!=null) for(File f:attachments){ MimeBodyPart fp=new MimeBodyPart(); try {
			fp.attachFile(f);
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} mp.addBodyPart(fp); }
	    msg.setContent(mp); Transport.send(msg);
	  }
	public static void send(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
	}



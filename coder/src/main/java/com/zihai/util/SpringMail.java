package com.zihai.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SpringMail {
	
	
	/**
	 * 带图片，附件
	 * */
	public void example1() throws MessagingException{
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");
		
		MimeMessage message = sender.createMimeMessage();
		
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("test@host.com");
		
		// use the true flag to indicate the text included is HTML
		helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
		
		// let's include the infamous windows Sample file (this time copied to c:/)
		FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addInline("identifier1234", res);
		//附件
		helper.addAttachment("CoolImage.jpg", res);
		
		sender.send(message);
	}
	
}


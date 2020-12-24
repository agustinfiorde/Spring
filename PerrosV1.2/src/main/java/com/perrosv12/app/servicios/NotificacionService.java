package com.perrosv12.app.servicios;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.perrosv12.app.entidades.Perro;
import com.perrosv12.app.repositorios.PerroRepository;

/*
 * Para permitir envio de email desde apps Java, SOLO APLICA A GMAIL
 * 
 * https://stackoverflow.com/questions/35347269/javax-mail-
 * authenticationfailedexception-535-5-7-8-username-and-password-not-ac
 */
@Service
public class NotificacionService {


	@Autowired
	private PerroRepository perroRepository;

	@Autowired
	private JavaMailSender mailsender;

	@Value("${spring.mail.username}")
	private String mailFrom;
	
	@Value("${spring.mail.password}")
	private String mailPassword;

	public boolean notificar(String id, String mailTo) {

		try {
			Perro perro = perroRepository.getOne(id);

			if (perro != null) {
//				enviarSimple(perro, mailTo);
				enviarHTML(perro, mailTo);

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("ERROR con " + e.getMessage());
			return false;
		}
	}

	@Async
	public void enviarSimple(Perro perro, String mailTo) {

		SimpleMailMessage mensaje = new SimpleMailMessage();

		mensaje.setTo(mailTo);
		mensaje.setFrom(mailFrom);
		mensaje.setSubject("Este es el mejor perro!");
		mensaje.setText(perro.getApodo());

		mailsender.send(mensaje);
	}

	@Async
	private void enviarHTML(Perro perro, String mailTo) {

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject("Mail Subject");

			String msg = "<h1>" + perro.getApodo() + "</h1>" + 
						 "<h2>" + perro.getRaza() + "</h2>" + 
						 "<img src="	+ perro.getFoto() + ">";
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			// DESCOMENTAR PARA ENVIAR ARCHIVOS DESDE EL SERVIDOR O BUCKET
			// MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			// attachmentBodyPart.attachFile(new File("pom.xml"));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			// multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

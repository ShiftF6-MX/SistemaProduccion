package mx.shf6.produccion.utilities;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	public static boolean enviarCorreo(Session session, ArrayList<String> destinatario, String asunto, String mensaje) {
		MimeMessage message = new MimeMessage(session);
		Transport transport = null;
		try {
			message.setFrom(new InternetAddress("atencion.clientes@mfg.com"));
			for (String d :destinatario)
				message.addRecipients(Message.RecipientType.TO, d);   
			message.setSubject(asunto);
			message.setContent(mensaje, "text/html");
			try {
				transport = session.getTransport("smtp");
		    } catch (MessagingException ex) {
		    	Notificacion.dialogoException(ex);
		    }//FIN TRY/CATCH
			transport.connect((String) session.getProperties().get("mail.smtp.host"), (String) session.getProperty("mail.smtp.user"), (String) session.getProperty("mail.smtp.clave"));
			transport.sendMessage(message, message.getAllRecipients());	        
			return true;
		} catch (Exception ex) {
			Notificacion.dialogoException(ex);
			return false; 
		}//FIN TRY/CATCH
	}//FIN METODO
}//FIN CLASE

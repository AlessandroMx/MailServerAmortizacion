package edu.itq.soa.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import edu.itq.soa.credit.TablaAmortizacion.Tabla;

public class Email {

    public static void createMail(String nombres, String apellidoPat,
            String apellidoMat, String correo, Tabla tabla) {

        // Datos del correo a utilizar
        final String fromEmail = "usuario.funcional.soa@gmail.com";
        final String password = "ufspassword";
        final String toEmail = correo;

        System.out.println("Empezando a generar correo");

        // Configurar propiedades del servidor del correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Se crea objeto autenticador para poder generar sesion
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, "Tabla de Amortización", EmailUtil
                .generateTableHtml(nombres, apellidoPat, apellidoMat, tabla));

    }

}

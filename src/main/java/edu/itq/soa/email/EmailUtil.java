package edu.itq.soa.email;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.itq.soa.credit.ClientData.Nombres;
import edu.itq.soa.credit.TablaAmortizacion.Tabla;

public class EmailUtil {

    /**
     * Utility method to send simple HTML email
     * 
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String toEmail,
            String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("admin@amortizacion.com",
                    "Admin-Amortizacion"));

            msg.setReplyTo(
                    InternetAddress.parse("admin@amortizacion.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setContent(body, "text/html; charset=utf-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail, false));
            System.out.println("Mensaje listo");
            Transport.send(msg);

            System.out.println("Email mandado exitosamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateTableHtml(String nombres, String apellidoPat,
            String apellidoMat, Tabla tabla) {

        String htmlHead = "<!DOCTYPE html><html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style></head>";
        String htmlPara = "<p>Estimado " + nombres + " " + apellidoPat + " "
                + apellidoPat
                + ", por este medio se adjunta la tabla de amortización solicitada.</p>";

        String htmlTablaTh = "<table><tr><th>Periodo</th><th>Saldo Inicial</th><th>Cuotas</th><th>Intereses</th><th>Abono Capital</th><th>Saldo Final</th></tr>";
        String htmlTablaContenido = "";

        for (Tabla.Registro registro : tabla.getRegistroArray()) {
            htmlTablaContenido += String.format(
                    "<tr><td>%d</td><td>%.2f</td><td>%.2f</td><td>%.2f</td><td>%.2f</td><td>%.2f</td></tr>",
                    registro.getPeriodo(), registro.getSaldoInicial(),
                    registro.getCuotas(), registro.getIntereses(),
                    registro.getAbonoCapital(), registro.getSaldoFinal());
        }

        String htmlBody = "<body>" + htmlPara + "<h3>Tabla Amortización</h3>"
                + htmlTablaTh + htmlTablaContenido + "</table></body></html>";

        return htmlHead + htmlBody;
    }

}

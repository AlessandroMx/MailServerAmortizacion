package edu.itq.soa.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import edu.itq.soa.credit.CreditResponseDocument;
import edu.itq.soa.email.Email;

public class ConsumerSpringListener implements MessageListener {

    /** Objeto para emisi�n de mensajes. */
    private JmsSender jmsSender;

    @Override
    public void onMessage(Message message) {

        try {
            final String msg = ((TextMessage) message).getText();

            CreditResponseDocument respDoc = CreditResponseDocument.Factory
                    .parse(msg);

            // Obtener datos del usuario generales
            String nombreUsuario = respDoc.getCreditResponse().getClient()
                    .getNombres();
            String apellidoPaternoUsuario = respDoc.getCreditResponse()
                    .getClient().getApellidoPaterno();
            String apellidoMaternoUsuario = respDoc.getCreditResponse()
                    .getClient().getApellidoMaterno();
            String correo = respDoc.getCreditResponse().getClient().getCorreo();

            System.out
                    .println(respDoc.getCreditResponse().getTabla().getTabla());

            // Enviar correo
            Email.createMail(nombreUsuario, apellidoPaternoUsuario,
                    apellidoMaternoUsuario, correo,
                    respDoc.getCreditResponse().getTabla().getTabla());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param jmsSender
     */
    public final void setJmsSender(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

}

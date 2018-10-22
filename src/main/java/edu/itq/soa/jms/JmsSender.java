/**
 * 
 */
package edu.itq.soa.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author tlopez Clase para envio de mensajes.
 */
public class JmsSender extends JmsTemplate {

	/**
	 * Envia un mensaje a la cola configurada.
	 * 
	 * @param message Mensaje a enviar.
	 */
	public void sendMessage(final String queue, final String message) {
		this.send(queue, new MessageCreator() {
			public Message createMessage(final Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});
	}
}

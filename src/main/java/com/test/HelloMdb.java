package com.test;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Message-Driven Bean implementation class for: HelloMDB.
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "PDDATestTopic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")

})
public class HelloMdb implements MessageListener, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(HelloMdb.class);

    @Override
    public void onMessage(Message message) {
        LOG.debug("HelloMDB::Message received: {}", message);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                LOG.debug("Message received: {}", textMessage.getText());
            } catch (JMSException e) {
                LOG.debug("Error while trying to consume messages: {}", e.getMessage());
            }
        }
    }

}

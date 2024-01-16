package uk.gov.hmcts.framework.services.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

/**
 * <p>
 * Title: TextMessageFactory.
 * </p>
 * <p>
 * Description: A factory for producing text messages, also includes there destination.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: EDS
 * </p>
 * 
 * @author Will Fardell
 * @version $Id: TextMessageFactory.java,v 1.1 2006/07/11 10:10:14 bzjrnl Exp $
 */
public abstract class TextMessageFactory extends MessageFactory {

    /**
     * Construct a new factory.
     * 
     * @param destination the destination of the messages
     */
    protected TextMessageFactory(String destination) {
        super(destination);
    }

    /**
     * Create a text message, populated using the concreate implementation of the populate method.
     * 
     * @param session the session used to create the message
     */
    @Override
    public Message create(Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        populate(message);
        return message;
    }

    /**
     * Concrete implementations populate the text message.
     * 
     * @param message the message to populate
     */
    public abstract void populate(TextMessage message) throws JMSException;

}

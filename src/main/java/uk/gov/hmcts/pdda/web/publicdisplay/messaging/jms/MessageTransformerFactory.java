package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;

/**
 * A factory class for transforming JMS messages to public display events. The current logic is
 * primitive.
 * 
 * <p>TextMessage: Received from mercator for daily list notifications ObjectMessage: Received from
 * midtier for configuration and data changes
 * 
 * <p>In the future this can use the JMSType message header to have a message repository
 * 
 * @author pznwc5
 */
public final class MessageTransformerFactory {

    /** Text message transformer. */
    private static MessageTransformer textMessageTransformer = new TextMessageTransformer();

    /** Object message transformer. */
    private static MessageTransformer objectMessageTransformer = new ObjectMessageTransformer();

    /**
     * Don't instantiate me.
     * 
     */
    private MessageTransformerFactory() {
        // private constructor
    }

    /**
     * Creates a transformer.
     * 
     * @param msg Message to be transformed
     * @return Message to be transformed
     */
    public static PublicDisplayEvent transform(Message msg) {
        if (msg instanceof TextMessage) {
            return textMessageTransformer.transform(msg);
        } else if (msg instanceof ObjectMessage) {
            return objectMessageTransformer.transform(msg);
        } else {
            throw new InvalidMessageException(msg);
        }
    }

}

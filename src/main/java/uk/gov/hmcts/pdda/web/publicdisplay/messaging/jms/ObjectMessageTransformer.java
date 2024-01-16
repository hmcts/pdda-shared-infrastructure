package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.UnexpectedJmsException;

import java.io.Serializable;

/**
 * Transformer for transforming object messages. Currently an object message is sent by the
 *         midtier when a configuration or hearing data change occurs.
 * @author pznwc5
 */
public class ObjectMessageTransformer implements MessageTransformer {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(ObjectMessageTransformer.class);

    /*
     * transform.
     * 
     * @see uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessageTransformer#
     * transformMessage( jakarta.jms.Message)
     */
    @Override
    public PublicDisplayEvent transform(Message msg) {
        try {
            if (!(msg instanceof ObjectMessage)) {
                throw new InvalidMessageException(msg);
            }

            ObjectMessage objMsg = (ObjectMessage) msg;
            log.debug("Object message received: " + objMsg);

            Serializable payload = objMsg.getObject();
            if (!(payload instanceof PublicDisplayEvent)) {
                throw new InvalidMessageException(payload);
            }
            log.debug("Public display event extracted: " + payload);

            return (PublicDisplayEvent) payload;

        } catch (JMSException ex) {
            log.error(ex.getMessage(), ex);
            throw new UnexpectedJmsException(ex);
        }
    }

}

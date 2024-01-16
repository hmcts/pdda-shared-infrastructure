package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.Message;
import uk.gov.hmcts.pdda.common.publicdisplay.exceptions.PublicDisplayRuntimeException;

/**
 * InvalidMessageException.
 * 
 * @author pznwc5 The exception is thrown when an unknown JMS message is received
 */
public class InvalidMessageException extends PublicDisplayRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * InvalidMessageException.
     * 
     * @param ex JMS message that is received
     */
    public InvalidMessageException(Message ex) {
        super("Invalid message type: " + ex.getClass());
    }

    /**
     * InvalidMessageException.
     * 
     * @param obj Object that is wrapped in the JMS message
     */
    public InvalidMessageException(Object obj) {
        super("Invalid object type: " + obj.getClass());
    }
}

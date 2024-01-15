package uk.gov.hmcts.pdda.web.publicdisplay.messaging;

import jakarta.jms.JMSException;
import uk.gov.hmcts.pdda.common.publicdisplay.exceptions.PublicDisplayRuntimeException;

/**
 * UnexpectedJmsException.
 * 
 * @author pznwc5 The exception is thrown when an unexpected JMSEXception occurs
 */
public class UnexpectedJmsException extends PublicDisplayRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * UnexpectedJmsException.
     * 
     * @param ex JMS message that is received
     */
    public UnexpectedJmsException(JMSException ex) {
        super(ex);
    }
}

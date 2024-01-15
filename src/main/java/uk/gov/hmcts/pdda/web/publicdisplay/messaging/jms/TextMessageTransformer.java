package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.common.publicdisplay.events.ConfigurationChangeEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;
import uk.gov.hmcts.pdda.common.publicdisplay.jms.PublicDisplayJmsConstants;
import uk.gov.hmcts.pdda.common.publicdisplay.types.configuration.CourtConfigurationChange;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.UnexpectedJmsException;

/**
 * Transformer for transforming text messages. Currently a text message is sent when a daily list is
 * processed by Mercator.
 * 
 * @author pznwc5
 */
public class TextMessageTransformer implements MessageTransformer {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(TextMessageTransformer.class);

    /*
     * transform.
     * 
     * @see uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessageTransformer#
     * transformMessage( jakarta.jms.Message)
     */
    @Override
    public PublicDisplayEvent transform(Message msg) {
        try {
            if (!(msg instanceof TextMessage)) {
                throw new InvalidMessageException(msg);
            }

            TextMessage txtMsg = (TextMessage) msg;
            log.debug("Text message received:{} ", txtMsg);

            // The property should be there as we use selector based on this
            int courtId = txtMsg.getIntProperty(PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME);
            log.debug("Court id: {}", courtId);

            // Create the court configuration change
            CourtConfigurationChange change = new CourtConfigurationChange(courtId, true);
            log.debug("Change: {}", change);

            // Create the event
            return new ConfigurationChangeEvent(change);

        } catch (JMSException ex) {
            log.error(ex.getMessage(), ex);
            throw new UnexpectedJmsException(ex);
        }
    }

}

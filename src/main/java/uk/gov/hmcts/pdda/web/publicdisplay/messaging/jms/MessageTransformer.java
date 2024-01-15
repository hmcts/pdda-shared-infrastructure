package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.Message;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;

/**
 * An interface for transforming JMS messages to public display events.
 * 
 * @author pznwc5
 */
public interface MessageTransformer {
    PublicDisplayEvent transform(Message msg);
}

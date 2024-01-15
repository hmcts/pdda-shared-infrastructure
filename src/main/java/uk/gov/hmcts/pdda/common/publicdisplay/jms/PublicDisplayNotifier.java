package uk.gov.hmcts.pdda.common.publicdisplay.jms;

import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.framework.business.services.CsMessageBeanNotifier;
import uk.gov.hmcts.pdda.common.publicdisplay.events.PublicDisplayEvent;

/**
 * PublicDisplayNotifier.
 * 
 * @author pznwc5
 * @version $Id: PublicDisplayNotifier.java,v 1.8 2006/06/05 12:28:24 bzjrnl Exp $
 */
public class PublicDisplayNotifier extends CsMessageBeanNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(PublicDisplayNotifier.class);
    
    /**
     * Sends a public display event.
     * 
     * @param event Public display event
     */
    public void sendMessage(PublicDisplayEvent event) {
        super.sendMessage(event);
        LOG.debug("sendMessage()");
    }
    
    @Override
    protected void setMessageHeader(ObjectMessage msg) throws JMSException {
        final PublicDisplayEvent event = (PublicDisplayEvent) msg.getObject();

        if (log.isDebugEnabled()) {
            log.debug("Message header:" + PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME + " : "
                + event.getCourtId());
        }

        msg.setIntProperty(PublicDisplayJmsConstants.COURT_ID_PROPERTY_NAME,
            event.getCourtId().intValue());
    }
}

package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.web.publicdisplay.initialization.servlet.InitializationService;

/**
 *         Handles a JMS exception schedules a re-initialization.
 * @author pznwc5
 */
public class Reinitializer implements ExceptionListener {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(Reinitializer.class);

    /**
     * Handles a JMS exception schedules a re-initialization.
     * 
     * @param ex JMS exception
     */
    @Override
    public void onException(JMSException ex) {
        LOG.error(ex.getMessage(), ex);
        InitializationService service = InitializationService.getInstance();
        try {
            // Destroy
            service.destroy();
        } finally {
            // Re-initialize
            service.initialize();
            LOG.info("Scheduled reinitialization");
        }
    }

}

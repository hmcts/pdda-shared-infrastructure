package uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.work.EventWorkManager;

/**
 * Common abstraction for P2P and publish/subscribe messaging.
 * 
 * @author meekun
 */
@SuppressWarnings("PMD.DoNotUseThreads")
public abstract class Subscription {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(Subscription.class);

    /** Court id for which the subscription is made. */
    private long courtId;

    /** Work manager used for the subscription. */
    private EventWorkManager eventWorkManager;

    /**
     * Returns the connection associated with the subscription.
     * 
     * @return JMS connection
     */
    protected abstract Connection getConnection();

    /**
     * Returns the consumer associated with subscription.
     * 
     * @return JMS subscription
     */
    protected abstract MessageConsumer getConsumer();

    /**
     * Factory method for getting a subscription.
     * 
     * @param courtId Court id for which the subscription is made
     * 
     * @return Subscription for receiving JMS messages
     */
    public static Subscription getSubscription(Topic topic, ConnectionFactory connectionFactory,
        long courtId, MessagingMode messagingMode) throws JMSException {
        Subscription sub;

        // Publish/SUbscribe subscription
        if (messagingMode.isPublishSubscription()) {
            sub = new PubSubSubscription(topic, connectionFactory, courtId);
            // P2P subscription for a Queue - this shouldn't be accessed in PDDA so can potentially
            // be
            // removed but may need some refactoring.s
        } else if (messagingMode.isPeer2Peer()) {
            sub = new P2PSubscription(courtId);
        } else {
            throw new IllegalArgumentException("Unknown messaging mode");
        }
        log.debug("Subscription type: {}", sub.getClass());

        return sub;
    }

    /**
     * Set the message listener.
     * 
     * @param listener Message listener
     */
    public void setMessageListener(MessageListener listener) throws JMSException {
        getConsumer().setMessageListener(listener);
        log.debug("Message listener set for court: " + courtId);
    }

    /**
     * Set the exception listener.
     * 
     * @param listener Exception listener
     */
    public void setExceptionListener(ExceptionListener listener) throws JMSException {
        getConnection().setExceptionListener(listener);
        log.debug("Exception listener set for court: " + courtId);
    }

    /**
     * Receives a message blockingly.
     * 
     * @return Message
     */
    public Message receive() throws JMSException {
        return getConsumer().receive();
    }

    /**
     * Receives a message blockingly.
     * 
     * @return Message
     */
    public Message receive(long timeout) throws JMSException {
        return getConsumer().receive(timeout);
    }

    /**
     * Starts JMS message reception.
     */
    public void start() throws JMSException {
        getConnection().start();
        log.debug("Connection started for court: " + courtId);
    }

    /**
     * Closes the subscription and shuts down the event manager.
     */
    public void close() {
        try {
            getConnection().close();
            log.debug("Connection closed for court: " + courtId);
        } catch (JMSException ex) {
            log.error(ex.getMessage(), ex);
        }
        if (eventWorkManager != null) {
            eventWorkManager.shutDown();
            log.debug("Work manager shutdown for court: " + courtId);
        }
    }

    /**
     * Starts the event work manager.
     */
    public void startEventWorkManager() {
        eventWorkManager.start();
        log.debug("Work manager started for court: " + courtId);
    }

    /**
     * Sets the work manager for the subscription.
     * 
     * @param eventWorkManager Work manager
     */
    public void setWorkManager(EventWorkManager eventWorkManager) {
        this.eventWorkManager = eventWorkManager;
        log.debug("Work manager set for court: " + courtId);
    }

    /**
     * Sets the court id.
     * 
     * @param courtId long
     */
    public void setCourtId(long courtId) {
        this.courtId = courtId;
    }

    /**
     * Returns the court id.
     * 
     * @return courtId
     */
    public long getCourtId() {
        return courtId;
    }

}

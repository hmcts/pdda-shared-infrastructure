package uk.gov.hmcts.pdda.web.publicdisplay.messaging;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.event.EventStore;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.event.EventStoreFactory;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessageReceiver;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.MessagingMode;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.Reinitializer;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.jms.Subscription;
import uk.gov.hmcts.pdda.web.publicdisplay.messaging.work.EventWorkManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class acts as the mediator for processing the asynchronous messages. The class manages the
 * JMS subscriptions and an event store used to buffer the events. The JMS subscriptions push the
 * messages to the event store which are subsequently processed using a thread pool.
 * 
 * @author pznwc5
 */
@SuppressWarnings("PMD.DoNotUseThreads")
public class MessageController {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    /** List of JMS subscriptions. */
    private List<Subscription> subscriptions = new ArrayList<>();

    /** Number of workers used by the subscriptions. */
    private final int numWorkers;

    /**
     * Creates a new MessageController object.
     * 
     * @param numWorkers The number of workers used by the thread pool.
     */
    public MessageController(int numWorkers) {
        this.numWorkers = numWorkers;
        log.info("Message controller created with no of workers: {}", numWorkers);
    }

    /**
     * Adds a JMS subscription.
     * 
     * @param topic Topic
     * @param connectionFactory The JMS connection factory
     */
    public void addSubscription(Topic topic, ConnectionFactory connectionFactory, long courtId,
        MessagingMode messagingMode) {
        try {
            // Create the event store for the subscription
            EventStore eventStore = EventStoreFactory.getEventStore();
            log.debug("Event store created for court: {}", courtId);

            // Create the event work manager
            EventWorkManager workManager = new EventWorkManager(eventStore, numWorkers);
            log.debug("Event work manager created for court: {}", courtId);

            // Create the JMS subscription and add it to the list of
            // subscriptions
            Subscription sub =
                Subscription.getSubscription(topic, connectionFactory, courtId, messagingMode);
            sub.setWorkManager(workManager);
            sub.setExceptionListener(new Reinitializer());
            subscriptions.add(sub);
            log.debug("JMS subscription created for court: {}", courtId);

            // Set the message listener and start the subscription
            MessageReceiver receiver = new MessageReceiver(eventStore, courtId);
            sub.setMessageListener(receiver);
            sub.start();
            log.debug("JMS subscription started for court: {}", courtId);
        } catch (JMSException ex) {
            throw new UnexpectedJmsException(ex);
        }
    }

    /**
     * Starts processing the event.
     */
    public void startEventProcessing() {
        for (Iterator<Subscription> it = getSubscriptions().iterator(); it.hasNext();) {
            Subscription sub = it.next();
            // Start event processing
            sub.startEventWorkManager();
            log.debug("Event processing started for court: {}", sub.getCourtId());
        }
    }

    /**
     * Shutdown all the subscriptions.
     * 
     */
    public void shutdown() {
        for (Iterator<Subscription> it = getSubscriptions().iterator(); it.hasNext();) {
            Subscription sub = it.next();
            // Close subscription
            sub.close();
            log.debug("Subscription closed for court: {}", sub.getCourtId());
        }
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
